package com.chapeaumoineau.miavortoj.feature.words.domain.use_case.favorite_language

import com.chapeaumoineau.miavortoj.feature.words.domain.model.FavoriteLanguage
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.FavoriteLanguageRepository

class DeleteFavoriteLanguageUseCase(private val repository: FavoriteLanguageRepository) {

    suspend operator fun invoke(language: FavoriteLanguage) {
        repository.deleteFavoriteLanguage(language)
    }

}