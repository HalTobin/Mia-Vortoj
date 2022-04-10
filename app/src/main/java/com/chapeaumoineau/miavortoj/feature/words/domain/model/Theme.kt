package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Theme (
    val title: String,
    val description: String,
    @PrimaryKey val id: Int? = null
)