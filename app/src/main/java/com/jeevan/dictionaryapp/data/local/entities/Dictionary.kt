package com.jeevan.dictionaryapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeevan.dictionaryapp.domain.model.DictionaryItem
import com.jeevan.dictionaryapp.domain.model.Meaning

@Entity
data class Dictionary(

    val meanings: List<Meaning>,
    @PrimaryKey(autoGenerate = false)
    val word: String
) {
    fun toDictionaryItem(): DictionaryItem {
        return DictionaryItem(
            meanings = meanings,
            word = word
        )
    }
}
