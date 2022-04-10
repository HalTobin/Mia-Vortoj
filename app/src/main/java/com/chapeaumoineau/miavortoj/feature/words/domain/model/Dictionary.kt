package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.cleanarchitecturenoteapp.R

@Entity
data class Dictionary (
    val title: String,
    val description: String,
    val language: Int? = null,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val languages = listOf("Esperanto", "English", "Français", "Español", "Italiano", "Slovenščina", "Русский", "日本")
        val flags = listOf(R.drawable.flag_eo, R.drawable.flag_gb, R.drawable.flag_fr, R.drawable.flag_es, R.drawable.flag_it, R.drawable.flag_ru, R.drawable.flag_jp)
    }
}

class InvalidDictionaryException(message: String): Exception(message)