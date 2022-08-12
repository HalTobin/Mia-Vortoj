package com.chapeaumoineau.miavortoj.domain.repository

import com.chapeaumoineau.miavortoj.domain.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    fun getWords(): Flow<List<Word>>

    fun getWordsFromDictionary(dictionaryId: Int): Flow<List<Word>>

    suspend fun getWordById(id: Int): Word?

    suspend fun getOldWordByDictionaryId(dictionaryId: Int): Word?

    suspend fun insertWord(word: Word)

    suspend fun changeWordLastTimestamp(id: Int, timestamp: Long)

    suspend fun deleteWordsFromDictionary(dictionaryId: Int)

    suspend fun deleteWord(word: Word)

}