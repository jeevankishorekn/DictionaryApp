package com.jeevan.dictionaryapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jeevan.dictionaryapp.data.local.entities.Dictionary

@Dao
interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDictionaryItem(item: Dictionary)

    @Query("SELECT * FROM dictionary where word= :word")
    suspend fun getDictionaryItem(word: String): List<Dictionary>

}