package com.chapeaumoineau.miavortoj.feature.words.presentation.quiz

sealed class QuizEvent {
    data class EnteredEntry(val value: String): QuizEvent()
    //data class ChangeEntryFocus(val focusState: FocusState): QuizEvent()
    object GetWord: QuizEvent()
    object CheckAnswer: QuizEvent()
}
