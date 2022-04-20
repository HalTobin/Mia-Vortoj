package com.chapeaumoineau.miavortoj.feature.words.presentation.quiz

sealed class QuizEvent {
    object GetWord: QuizEvent()
}
