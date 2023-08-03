package com.jeevan.dictionaryapp.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeevan.dictionaryapp.domain.model.Meaning

class JsonTypeConverters {

    @TypeConverter
    fun fromJsonToMeaning(jsonString: String?): List<Meaning> {
        val type = object : TypeToken<List<Meaning>>() {}.type
        return Gson().fromJson(jsonString, type)
    }

    @TypeConverter
    fun fromMeaningToJson(meaning: List<Meaning?>): String {
        return Gson().toJson(meaning)
    }
}