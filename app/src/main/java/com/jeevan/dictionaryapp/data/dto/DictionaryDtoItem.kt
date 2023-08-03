package com.jeevan.dictionaryapp.data.dto

import com.google.gson.annotations.SerializedName
import com.jeevan.dictionaryapp.domain.model.DictionaryItem

data class DictionaryDtoItem(
    @SerializedName("license")
    val licenseDto: LicenseDto,
    @SerializedName("meanings")
    val meaningDtos: List<MeaningDto>,
    @SerializedName("phonetics")
    val phoneticDtos: List<PhoneticDto>,
    val sourceUrls: List<String>,
    val word: String
)

fun DictionaryDtoItem.toDictionaryItem(): DictionaryItem {
    return DictionaryItem(
        meanings = meaningDtos.map { it.toMeaning() },
        word = word
    )
}