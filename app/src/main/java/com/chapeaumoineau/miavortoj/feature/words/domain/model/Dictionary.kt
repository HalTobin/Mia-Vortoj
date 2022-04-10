package com.chapeaumoineau.miavortoj.feature.words.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chapeaumoineau.miavortoj.R

@Entity
data class Dictionary (
    val title: String,
    val description: String,
    val language: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val languages = listOf("Esperanto", "English", "Français", "Español", "Italiano", "Deutsch","Slovenščina", "Русский", "日本")
        val flags = listOf(R.drawable.flag_eo,
            R.drawable.flag_gb,
            R.drawable.flag_fr,
            R.drawable.flag_es,
            R.drawable.flag_it,
            R.drawable.flag_de,
            R.drawable.flag_ru,
            R.drawable.flag_jp)
        val colors = listOf(Color(0xFF009900),
            Color(0xFF00247D),
            Color(0xFF002395),
            Color(0xFFFFc400),
            Color(0xFF009246),
            Color(0xFFDD0000),
            Color(0xFF33348E),
            Color(0xFF0039A6),
            Color(0xFFFFFFFF))
    }
}

class InvalidDictionaryException(message: String): Exception(message)