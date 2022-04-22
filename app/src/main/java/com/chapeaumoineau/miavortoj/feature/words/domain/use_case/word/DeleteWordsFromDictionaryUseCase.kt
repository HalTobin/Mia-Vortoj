package com.chapeaumoineau.miavortoj.feature.words.domain.use_case.word

import com.chapeaumoineau.miavortoj.feature.words.domain.repository.WordRepository

class DeleteWordsFromDictionaryUseCase(private val repository: WordRepository) {

    suspend operator fun invoke(dictionaryId: Int) {
        repository.deleteWordsFromDictionary(dictionaryId)
    }

}