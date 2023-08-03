package com.jeevan.dictionaryapp.domain.usecase.dictionaryItemUsecase

import android.util.Log
import com.jeevan.dictionaryapp.core.Result
import com.jeevan.dictionaryapp.data.dto.toDictionaryItem
import com.jeevan.dictionaryapp.data.local.dao.DictionaryDao
import com.jeevan.dictionaryapp.domain.model.DictionaryItem
import com.jeevan.dictionaryapp.domain.repository.DictionaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DictionaryItemUseCase @Inject constructor(
    private val repository: DictionaryRepository,
    private val dao: DictionaryDao
) {

    operator fun invoke(word: String): Flow<Result<DictionaryItem>> {
        return flow {
            try {
                emit(Result.Loading())
                val data = repository.getDictionaryItem(word)[0].toDictionaryItem()
                CoroutineScope(Dispatchers.IO).launch {
                    dao.insertDictionaryItem(data.toDictionary())
                }
                Log.d("Jeeeevan", "invoke: $data ")
                emit(Result.Success(data))
            } catch (e: Exception) {
                val data = dao.getDictionaryItem(word)
                if (data.isNotEmpty()) {
                    emit(Result.Success(data[0].toDictionaryItem()))
                } else
                    emit(Result.Failure(e.localizedMessage ?: "Exception caught"))
            }
        }
    }
}