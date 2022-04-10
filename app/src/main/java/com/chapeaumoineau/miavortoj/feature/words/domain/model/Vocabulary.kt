package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Vocabulary (
    val intVal: String,
    val outVal: String,
    val notes: String,
    //@ForeignKey val dictionaryId: Int,
    //@ForeignKey val themeId: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object
}