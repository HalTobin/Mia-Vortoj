package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteLanguage(
    val iso: String,
    val timestamp: Int,
    @PrimaryKey val id: Int? = null
)