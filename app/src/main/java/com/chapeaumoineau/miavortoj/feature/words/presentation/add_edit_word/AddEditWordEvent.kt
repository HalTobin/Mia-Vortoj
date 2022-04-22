package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word

sealed class AddEditWordEvent{
    data class EnteredSource(val value: String): AddEditWordEvent()
    data class EnteredTarget(val value: String): AddEditWordEvent()
    data class EnteredDescription(val value: String): AddEditWordEvent()
    data class EnteredEmote(val value: String): AddEditWordEvent()
    data class EnteredSearch(val value: String): AddEditWordEvent()
    object MoreCategory: AddEditWordEvent()
    data class OnNewCategorySelected(val category: Int): AddEditWordEvent()
    object DismissCategoryDialog: AddEditWordEvent()
    data class ChangeDifficulty(val difficulty: Int): AddEditWordEvent()
    object SaveWord: AddEditWordEvent()
}
