package com.chapeaumoineau.miavortoj.feature.words.data.repository

import com.chapeaumoineau.miavortoj.feature.words.data.data_source.FavoriteLanguageDao
import com.chapeaumoineau.miavortoj.feature.words.domain.model.FavoriteLanguage
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.FavoriteLanguageRepository
import kotlinx.coroutines.flow.Flow

class FavoriteLanguageRepositoryImpl(private val dao: FavoriteLanguageDao) : FavoriteLanguageRepository {
    override fun getFavoriteLanguages(): Flow<List<FavoriteLanguage>> {
        return dao.getFavoriteLanguages()
    }

    override suspend fun getFavoriteLanguageById(id: Int): FavoriteLanguage? {
        return dao.getFavoriteLanguageById(id)
    }

    override suspend fun insertFavoriteLanguage(favoriteLanguage: FavoriteLanguage) {
        dao.insertFavoriteLanguage(favoriteLanguage)
    }

    override suspend fun deleteFavoriteLanguage(favoriteLanguage: FavoriteLanguage) {
        dao.deleteFavoriteLanguage(favoriteLanguage)
    }
}