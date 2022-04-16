package com.chapeaumoineau.miavortoj.feature.words.data.data_source

import androidx.room.*
import com.chapeaumoineau.miavortoj.feature.words.domain.model.FavoriteLanguage
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteLanguageDao {

    @Query("SELECT * FROM favoriteLanguage")
    fun getFavoriteLanguages(): Flow<List<FavoriteLanguage>>

    @Query("SELECT * FROM favoriteLanguage WHERE id = :id")
    suspend fun getFavoriteLanguageById(id: Int): FavoriteLanguage?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteLanguage(favoriteLanguage: FavoriteLanguage)

    @Delete
    suspend fun deleteFavoriteLanguage(favoriteLanguage: FavoriteLanguage)
}