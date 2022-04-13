package com.chapeaumoineau.miavortoj.feature.words.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word

@Database(
    entities = [Dictionary::class, Word::class],
    version = 1
)
abstract class VocabularyDatabase: RoomDatabase() {

    abstract val dictionaryDao: DictionaryDao
    abstract val wordDao: WordDao

    companion object {
        const val DATABASE_NAME = "vocabulary_db"
    }
}