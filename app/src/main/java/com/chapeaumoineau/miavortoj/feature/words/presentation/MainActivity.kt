package com.chapeaumoineau.miavortoj.feature.words.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word.AddEditWordScreen
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.AddEditDictionaryScreen
import com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.DictionariesScreen
import com.chapeaumoineau.miavortoj.feature.words.presentation.quiz.QuizScreen
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.Screen
import com.chapeaumoineau.miavortoj.feature.words.presentation.word_card.WordCardScreen
import com.chapeaumoineau.miavortoj.feature.words.presentation.words.WordsScreen
import com.chapeaumoineau.miavortoj.ui.theme.VortojAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VortojAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.DictionariesScreen.route) {
                        composable(route = Screen.DictionariesScreen.route) {
                            DictionariesScreen(navController = navController)
                        }
                        composable(route = Screen.AddEditDictionaryScreen.route + "?dictionaryId={dictionaryId}&dictionaryLanguage={dictionaryLanguage}",
                            arguments = listOf(navArgument(name = "dictionaryId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(name = "dictionaryLanguage") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            )
                        ) {
                            AddEditDictionaryScreen(navController = navController)
                        }
                        composable(route = Screen.WordsScreen.route + "?dictionaryId={dictionaryId}&dictionaryLanguage={dictionaryLanguage}",
                            arguments = listOf(navArgument(name = "dictionaryId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(name = "dictionaryLanguage") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            )
                        ) {
                            WordsScreen(navController = navController)
                            }
                        composable(route = Screen.AddEditWordScreen.route + "?dictionaryId={dictionaryId}&wordId={wordId}", arguments = listOf(
                            navArgument(name = "dictionaryId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(name = "wordId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },)) {
                            AddEditWordScreen(navController = navController)
                        }
                        composable(route = Screen.WordCardScreen.route + "?wordId={wordId}", arguments = listOf(
                            navArgument(name = "wordId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )) {
                            WordCardScreen(navController = navController)
                        }
                        composable(route = Screen.QuizScreen.route + "?dictionaryId={dictionaryId}", arguments = listOf(
                            navArgument(name = "dictionaryId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )) {
                            QuizScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
