package com.chapeaumoineau.miavortoj.feature.words.domain.use_case.favorite_language

import com.chapeaumoineau.miavortoj.feature.words.domain.model.FavoriteLanguage
import com.chapeaumoineau.miavortoj.feature.words.domain.model.InvalidDictionaryException
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.FavoriteLanguageRepository

class AddFavoriteLanguageUseCase(private val repository: FavoriteLanguageRepository) {

    @Throws(InvalidDictionaryException::class)
    suspend operator fun invoke(language: FavoriteLanguage) {
        if(language.iso.isBlank()) {
            throw InvalidDictionaryException("The source word field can't be empty.")
        }
        repository.insertFavoriteLanguage(language)
    }
}