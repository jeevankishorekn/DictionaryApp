package com.jeevan.dictionaryapp.data.remote

import com.jeevan.dictionaryapp.data.dto.DictionaryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryAPI {

    @GET("{word}")
    suspend fun getDictionaryItem(@Path("word") word: String): DictionaryDto

}