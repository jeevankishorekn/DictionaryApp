package com.jeevan.dictionaryapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jeevan.dictionaryapp.data.local.converters.JsonTypeConverters
import com.jeevan.dictionaryapp.data.local.dao.DictionaryDao
import com.jeevan.dictionaryapp.data.local.entities.Dictionary

@Database(
    entities = [Dictionary::class],
    version = 1
)
@TypeConverters(JsonTypeConverters::class)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract val dao: DictionaryDao
}