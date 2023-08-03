package com.jeevan.dictionaryapp.presentation.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeevan.dictionaryapp.core.Result
import com.jeevan.dictionaryapp.domain.model.DictionaryItem
import com.jeevan.dictionaryapp.domain.usecase.dictionaryItemUsecase.DictionaryItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val dictionaryItemUseCase: DictionaryItemUseCase,
    private val context: Application
) : AndroidViewModel(context) {

    private var _isLoading = MutableLiveData(false)
    val isLoading = _isLoading as LiveData<Boolean>

    private var _data = MutableLiveData<DictionaryItem>()
    val data = _data as LiveData<DictionaryItem>

    fun getDictionaryItem(word: String) {
        viewModelScope.launch {
            val result = dictionaryItemUseCase(word)
            result.collect { item ->
                when (item) {
                    is Result.Success -> {
                        _data.postValue(item.data!!)
                        _isLoading.postValue(false)
                    }

                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }

                    is Result.Failure -> {
                        _isLoading.postValue(false)
                        Toast.makeText(context.applicationContext, item.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    fun getRandomDarkColor(): String {
        val random = Random(System.currentTimeMillis())

        // Define the upper limit for each color component to make it dark.
        // You can adjust these values to control the darkness level of the generated color.
        val maxRed = 128
        val maxGreen = 128
        val maxBlue = 128

        val red = random.nextInt(0, maxRed)
        val green = random.nextInt(0, maxGreen)
        val blue = random.nextInt(0, maxBlue)

        // Convert the color components to hexadecimal strings and concatenate them.
        return String.format("#%02X%02X%02X", red, green, blue)
    }

    fun getRandomLightColor(): String {
        val random = Random(System.currentTimeMillis())

        // Define the upper limit for each color component to make it dark.
        // You can adjust these values to control the darkness level of the generated color.
        val maxRed = 255
        val maxGreen = 255
        val maxBlue = 255

        val red = random.nextInt(200, maxRed)
        val green = random.nextInt(200, maxGreen)
        val blue = random.nextInt(200, maxBlue)

        // Convert the color components to hexadecimal strings and concatenate them.
        return String.format("#%02X%02X%02X", red, green, blue)
    }
}