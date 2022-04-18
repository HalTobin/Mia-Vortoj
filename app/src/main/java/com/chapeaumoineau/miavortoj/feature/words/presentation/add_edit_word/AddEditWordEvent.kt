package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word

import androidx.compose.ui.focus.FocusState

sealed class AddEditWordEvent{
    data class EnteredSource(val value: String): AddEditWordEvent()
    data class ChangeSourceFocus(val focusState: FocusState): AddEditWordEvent()
    data class EnteredTarget(val value: String): AddEditWordEvent()
    data class ChangeTargetFocus(val focusState: FocusState): AddEditWordEvent()
    data class EnteredDescription(val value: String): AddEditWordEvent()
    data class ChangeDescriptionFocus(val focusState: FocusState): AddEditWordEvent()
    data class EnteredEmote(val value: String): AddEditWordEvent()
    data class ChangeEmoteFocus(val focusState: FocusState): AddEditWordEvent()
    data class EnteredSearch(val value: String): AddEditWordEvent()
    object MoreCategory: AddEditWordEvent()
    data class OnNewCategorySelected(val category: Int): AddEditWordEvent()
    object DismissCategoryDialog: AddEditWordEvent()
    object SaveWord: AddEditWordEvent()
}
