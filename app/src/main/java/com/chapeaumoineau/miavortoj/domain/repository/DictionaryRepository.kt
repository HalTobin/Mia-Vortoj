package com.chapeaumoineau.miavortoj.domain.repository

import com.chapeaumoineau.miavortoj.domain.model.Dictionary
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {

    fun getDictionaries(): Flow<List<Dictionary>>

    suspend fun getDictionaryById(id: Int): Dictionary?

    suspend fun insertDictionary(dictionary: Dictionary)

    suspend fun deleteDictionary(dictionary: Dictionary)

}