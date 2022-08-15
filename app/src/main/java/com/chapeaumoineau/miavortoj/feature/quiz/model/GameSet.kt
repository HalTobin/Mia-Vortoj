package com.chapeaumoineau.miavortoj.feature.quiz.model

import com.chapeaumoineau.miavortoj.domain.model.Word

data class GameSet(
    val rules: Rules,
    var currentIndex: Int = 0,
    var words: List<Word> = listOf()
) {

    fun setGameWords(wordsList: List<Word>) {
        words = wordsList
        rules.duration = words.size
    }

    fun getWordCurrentWord(): Word {
        return words[currentIndex]
    }

    fun isEnd(): Boolean {
        return (currentIndex+1 >= rules.duration)
    }

    fun next(): Word? {
        currentIndex++
        return if (currentIndex >= rules.duration) null
        else words[currentIndex]
    }

}