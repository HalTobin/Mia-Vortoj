package com.chapeaumoineau.miavortoj.feature.words.domain.use_case.dictionary

import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.DictionaryRepository

class DeleteDictionaryUseCase(private val repository: DictionaryRepository) {

    suspend operator fun invoke(dictionary: Dictionary) {
        repository.deleteDictionary(dictionary)
    }

}