package com.chapeaumoineau.miavortoj.feature.words.domain.use_case

import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.model.InvalidDictionaryException
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.DictionaryRepository

class AddDictionaryUseCase(private val repository: DictionaryRepository) {

    @Throws(InvalidDictionaryException::class)
    suspend operator fun invoke(dictionary: Dictionary) {
        if(dictionary.title.isBlank()) {
            throw InvalidDictionaryException("The title of the dictionary can't be empty.")
        }
        if(dictionary.language == null) {
            throw InvalidDictionaryException("The language of the dictionary can't be undetermined.")
        }
        repository.insertDictionary(dictionary)
    }
}