package com.jeevan.dictionaryapp.domain.model

data class Meaning(
    val definitions: List<Definition> = emptyList(),
    val partOfSpeech: String,
    val synonyms: List<String> = emptyList()
)