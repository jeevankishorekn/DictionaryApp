package com.jeevan.dictionaryapp.data.dto

import com.jeevan.dictionaryapp.domain.model.Definition

data class DefinitionDto(
    val antonyms: List<String>? = emptyList(),
    val definition: String?,
    val example: String?,
    val synonyms: List<String>? = emptyList()
)

fun DefinitionDto.toDefinition(): Definition {
    return Definition(
        definition = definition ?: "",
        example = example ?: "",
        synonyms = synonyms ?: emptyList()
    )
}