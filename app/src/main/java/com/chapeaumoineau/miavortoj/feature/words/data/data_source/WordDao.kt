package com.chapeaumoineau.miavortoj.feature.words.data.data_source

import androidx.room.*
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word")
    fun getWords(): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE dictionaryId = :dictionaryId")
    fun getWordsFromDictionary(dictionaryId: Int): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE id = :id")
    suspend fun getWordById(id: Int): Word?

    @Query("SELECT * FROM word WHERE lastTestTimestamp = (SELECT MIN(lastTestTimestamp) FROM word WHERE dictionaryId = :dictionaryId)")
    suspend fun getOldWordByDictionaryId(dictionaryId: Int): Word?

    @Query("UPDATE word SET lastTestTimestamp = :timestamp WHERE id = :id")
    suspend fun changeWordLastTimestamp(id: Int, timestamp: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)
}