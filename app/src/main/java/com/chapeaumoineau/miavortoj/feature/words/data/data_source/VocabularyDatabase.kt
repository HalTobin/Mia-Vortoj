package com.chapeaumoineau.miavortoj.feature.words.data.data_source

import android.content.ContentValues
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.model.FavoriteLanguage
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Word


@Database(
    entities = [Dictionary::class, Word::class, FavoriteLanguage::class],
    version = 2
)
abstract class VocabularyDatabase: RoomDatabase() {

    abstract val dictionaryDao: DictionaryDao
    abstract val wordDao: WordDao
    abstract val favoriteLanguageDao: FavoriteLanguageDao

    companion object {
        const val DATABASE_NAME = "vocabulary_db"

        val prepopulate = object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                val contentValues = ContentValues()
                contentValues.put("id", 1)
                contentValues.put("iso", "DEU")
                contentValues.put("timestamp", 1)
                db.insert("favoriteLanguage", OnConflictStrategy.IGNORE, contentValues)

                contentValues.put("id", 2)
                contentValues.put("iso", "FRA")
                contentValues.put("timestamp", 2)
                db.insert("favoriteLanguage", OnConflictStrategy.IGNORE, contentValues)

                contentValues.put("id", 3)
                contentValues.put("iso", "ESP")
                contentValues.put("timestamp", 3)
                db.insert("favoriteLanguage", OnConflictStrategy.IGNORE, contentValues)

                contentValues.put("id", 4)
                contentValues.put("iso", "ENG")
                contentValues.put("timestamp", 4)
                db.insert("favoriteLanguage", OnConflictStrategy.IGNORE, contentValues)
            }
        }
    }

}