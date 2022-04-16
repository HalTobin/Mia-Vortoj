package com.chapeaumoineau.miavortoj.feature.words.domain.repository

import com.chapeaumoineau.miavortoj.feature.words.domain.model.FavoriteLanguage
import kotlinx.coroutines.flow.Flow

interface FavoriteLanguageRepository {

    fun getFavoriteLanguages(): Flow<List<FavoriteLanguage>>

    suspend fun getFavoriteLanguageById(id: Int): FavoriteLanguage?

    suspend fun insertFavoriteLanguage(favoriteLanguage: FavoriteLanguage)

    suspend fun deleteFavoriteLanguage(favoriteLanguage: FavoriteLanguage)

}