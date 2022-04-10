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
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.AddEditDictionaryScreen
import com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.DictionariesScreen
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.Screen
import com.chapeaumoineau.miavortoj.ui.theme.VortojAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
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
                            val language = it.arguments?.getInt("dictionaryLanguage") ?: -1
                            AddEditDictionaryScreen(navController = navController, dictionaryLanguage = language)
                        }
                    }
                }
            }
        }
    }
}
