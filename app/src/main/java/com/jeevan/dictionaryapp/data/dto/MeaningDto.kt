package com.jeevan.dictionaryapp.data.dto

import com.google.gson.annotations.SerializedName
import com.jeevan.dictionaryapp.domain.model.Meaning

data class MeaningDto(
    val antonyms: List<String>? = emptyList(),
    @SerializedName("definitions")
    val definitionDtos: List<DefinitionDto>? = emptyList(),
    val partOfSpeech: String?,
    val synonyms: List<String>? = emptyList()
)

fun MeaningDto.toMeaning(): Meaning {
    return Meaning(
        definitions = definitionDtos!!.map { it.toDefinition() },
        partOfSpeech = partOfSpeech ?: "",
        synonyms = synonyms ?: emptyList()
    )
}