package com.jeevan.dictionaryapp.domain.repository

import com.jeevan.dictionaryapp.data.dto.DictionaryDto

interface DictionaryRepository {

    suspend fun getDictionaryItem(word: String): DictionaryDto

}