package com.chapeaumoineau.miavortoj.feature.words.presentation.util

sealed class Screen(val route: String) {
    object DictionariesScreen: Screen("dictionaries_screen")
    object AddEditDictionaryScreen: Screen("add_edit_dictionary_screen")
    object WordsScreen: Screen("words_screen")
    object AddEditWordScreen: Screen("add_edit_word_screen")
    object WordCardScreen: Screen("word_card_screen")
}