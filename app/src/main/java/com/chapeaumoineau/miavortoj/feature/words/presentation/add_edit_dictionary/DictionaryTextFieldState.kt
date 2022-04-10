package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary

data class DictionaryTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
