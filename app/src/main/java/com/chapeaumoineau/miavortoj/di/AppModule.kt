package com.chapeaumoineau.miavortoj.di

import android.app.Application
import androidx.room.Room
import com.chapeaumoineau.miavortoj.feature.words.data.data_source.VocabularyDatabase
import com.chapeaumoineau.miavortoj.feature.words.data.repository.DictionaryRepositoryImpl
import com.chapeaumoineau.miavortoj.feature.words.data.repository.WordRepositoryImpl
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.DictionaryRepository
import com.chapeaumoineau.miavortoj.feature.words.domain.repository.WordRepository
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.*
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.dictionary.AddDictionaryUseCase
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.dictionary.DeleteDictionaryUseCase
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.dictionary.GetDictionariesUseCase
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.dictionary.GetDictionaryUseCase
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.word.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideVocabularyDatabase(app: Application): VocabularyDatabase {
        return Room.databaseBuilder(app, VocabularyDatabase::class.java, VocabularyDatabase.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideDictionaryRepository(db: VocabularyDatabase): DictionaryRepository {
        return DictionaryRepositoryImpl(db.dictionaryDao)
    }

    @Provides
    @Singleton
    fun provideDictionaryUseCases(repository: DictionaryRepository): DictionaryUseCases {
        return DictionaryUseCases(getDictionaries = GetDictionariesUseCase(repository), deleteDictionary = DeleteDictionaryUseCase(repository), addDictionary = AddDictionaryUseCase(repository), getDictionary = GetDictionaryUseCase(repository))
    }

    @Provides
    @Singleton
    fun provideWordRepository(db: VocabularyDatabase): WordRepository {
        return WordRepositoryImpl(db.wordDao)
    }

    @Provides
    @Singleton
    fun provideWordUseCases(repository: WordRepository): WordUseCases {
        return WordUseCases(getWords = GetWordsUseCase(repository), getWordsFromDictionary = GetWordsFromDictionaryUseCase(repository),deleteWord = DeleteWordUseCase(repository), addWord = AddWordUseCase(repository), getWord = GetWordUseCase(repository))
    }
}