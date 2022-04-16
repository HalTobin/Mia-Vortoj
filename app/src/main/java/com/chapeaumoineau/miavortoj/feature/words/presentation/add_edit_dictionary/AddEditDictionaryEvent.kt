package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary

import androidx.compose.ui.focus.FocusState

sealed class AddEditDictionaryEvent{
    data class EnteredTitle(val value: String): AddEditDictionaryEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditDictionaryEvent()
    data class EnteredDescription(val value: String): AddEditDictionaryEvent()
    data class ChangeDescriptionFocus(val focusState: FocusState): AddEditDictionaryEvent()
    data class ChangeLanguage(val language: String): AddEditDictionaryEvent()
    object MoreLanguage: AddEditDictionaryEvent()
    data class OnNewLanguageSelected(val language: String): AddEditDictionaryEvent()
    object SaveDictionary: AddEditDictionaryEvent()
}
