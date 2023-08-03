package com.jeevan.dictionaryapp.domain.model

import com.jeevan.dictionaryapp.data.local.entities.Dictionary

data class DictionaryItem(
    val meanings: List<Meaning> = emptyList(),
    val word: String
) {
    fun toDictionary(): Dictionary {
        return Dictionary(
            meanings = meanings,
            word = word
        )
    }
}