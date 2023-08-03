package com.jeevan.dictionaryapp.data.dto

import com.google.gson.annotations.SerializedName

data class PhoneticDto(
    val audio: String,
    @SerializedName("license")
    val licenseDto: LicenseDto,
    val sourceUrl: String,
    val text: String
)