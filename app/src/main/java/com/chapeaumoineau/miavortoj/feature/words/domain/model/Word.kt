package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Word (
    val sourceWord: String,
    val targetWord: String,
    val emote: String,
    val notes: String,
    val mastery: Int,
    val dictionaryId: Int,
    val themeId: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val themes = listOf(
            "Chiffres",
            "Maison",
            "Nourriture",
            "Salutations / Aurevoire")
    }
}