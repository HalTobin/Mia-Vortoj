package com.chapeaumoineau.miavortoj.domain.util

import com.chapeaumoineau.miavortoj.domain.model.Word

fun MutableList<Word>.addMostPertinentWords(wordsFromDB: MutableList<Word>, duration: Int) {

    for (i in 0 until duration) {
        var maxScore = 0L
        var mostPertinentWord: Word? = null

        wordsFromDB.forEach {
            if (it.getScore() > maxScore) {
                mostPertinentWord = it
                maxScore = it.getScore()
            }
        }

        wordsFromDB.remove(mostPertinentWord)

        this.add(mostPertinentWord!!)
    }

}

class Utils {


}