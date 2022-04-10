package com.chapeaumoineau.miavortoj.feature.words.data.repository

import com.chapeaumoineau.miavortoj.feature.words.data.data_source.DictionaryDao
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow

class DictionaryRepositoryImpl(private val dao: DictionaryDao) : DictionaryRepository {
    override fun getDictionaries(): Flow<List<Dictionary>> {
        return dao.getDictionaries()
    }

    override suspend fun getDictionaryById(id: Int): Dictionary? {
        return dao.getDictionaryById(id)
    }

    override suspend fun insertDictionary(dictionary: Dictionary) {
        dao.insertDictionary(dictionary)
    }

    override suspend fun deleteDictionary(dictionary: Dictionary) {
        dao.deleteDictionary(dictionary)
    }
}