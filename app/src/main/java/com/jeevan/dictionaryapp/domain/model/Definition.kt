package com.jeevan.dictionaryapp.domain.model

data class Definition(
    val definition: String,
    val example: String,
    val synonyms: List<String> = emptyList()
)