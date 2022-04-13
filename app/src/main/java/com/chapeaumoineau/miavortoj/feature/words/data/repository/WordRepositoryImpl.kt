package com.chapeaumoineau.miavortoj.feature.words.data.repository

import com.chapeaumoineau.miavortoj.feature.words.data.data_source.WordDao
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class WordRepositoryImpl(private val dao: WordDao) : WordRepository {
    override fun getWords(): Flow<List<Word>> {
        return dao.getWords()
    }

    override fun getWordsFromDictionary(dictionaryId: Int): Flow<List<Word>> {
        return dao.getWordsFromDictionary(dictionaryId)
    }

    override suspend fun getWordById(id: Int): Word? {
        return dao.getWordById(id)
    }

    override suspend fun insertWord(word: Word) {
        dao.insertWord(word)
    }

    override suspend fun deleteWord(word: Word) {
        dao.deleteWord(word)
    }
}