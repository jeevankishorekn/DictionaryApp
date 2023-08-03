package com.jeevan.dictionaryapp.data.repository

import android.util.Log
import com.jeevan.dictionaryapp.data.dto.DictionaryDto
import com.jeevan.dictionaryapp.data.remote.DictionaryAPI
import com.jeevan.dictionaryapp.domain.repository.DictionaryRepository
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(private val api: DictionaryAPI) :
    DictionaryRepository {

    override suspend fun getDictionaryItem(word: String): DictionaryDto {
        Log.d("jeeevan", "getDictionaryItem: $word ,  ")
        return api.getDictionaryItem(word)
    }
}