package com.chapeaumoineau.miavortoj.feature.quiz.model

import com.chapeaumoineau.miavortoj.domain.model.Word
import kotlin.random.Random

/**
 * @param rules
 * @param currentIndex
 * @param answers
 */
data class GameSet(
    val rules: Rules,
    var currentIndex: Int = 0,
    var answers: MutableList<Answer> = mutableListOf()
) {

    // Generate the list of question / answers for the test
    fun setGameWords(wordsList: List<Word>) {
        wordsList.forEach {
            /* Use a random boolean to choose if the user will be asked to find the word
            in its language, or in the language he's learning */
            if (Random.nextBoolean()) answers.add(
                Answer(
                    isFromTarget = false,
                    wordId = it.id!!,
                    question = it.sourceWord,
                    answer = it.targetWord,
                    emote = it.emote,
                    played = it.nbPlayed,
                    succeed = it.nbSucceed
                )
            )
            else answers.add(
                Answer(
                    isFromTarget = true,
                    wordId = it.id!!,
                    question = it.targetWord,
                    answer = it.sourceWord,
                    played = it.nbPlayed,
                    succeed = it.nbSucceed
                )
            )
        }

        rules.duration = answers.size
    }

    fun getCurrentAnswer(): Answer {
        return answers[currentIndex]
    }

    fun isEnd(): Boolean {
        return (currentIndex + 1 >= rules.duration)
    }

    fun next(): Answer? {
        currentIndex++
        return if (currentIndex >= rules.duration) null
        else answers[currentIndex]
    }

}