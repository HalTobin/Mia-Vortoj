package com.chapeaumoineau.miavortoj.feature.words.presentation.words

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.DictionariesEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.Screen
import com.chapeaumoineau.miavortoj.feature.words.presentation.words.components.WordItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordsScreen(navController: NavController,
                viewModel:WordsViewModel = hiltViewModel()) {

    val state = viewModel.state.value

    val dictionaryTitle = viewModel.title.value
    val dictionaryDescription = viewModel.description.value
    val dictionaryLanguageIdState = viewModel.dictionaryLanguage.value

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            //navController.navigate(Screen.AddEditDictionaryScreen.route)
        }, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add word")
        }
    }, scaffoldState = scaffoldState) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Dictionary.colors[dictionaryLanguageIdState])) {
            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(Dictionary.flags_simple[dictionaryLanguageIdState]),
                    contentDescription = "",
                    modifier = Modifier
                        .size(65.dp)
                        .shadow(15.dp, CircleShape)
                        .clip(CircleShape)
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = CircleShape
                        )
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = dictionaryTitle.text,
                        style = MaterialTheme.typography.h5,
                        color = Color.Black)
                    Text(text = dictionaryDescription.text,
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.Black)
                }
                IconButton(onClick = {
                    //viewModel.onEvent(WordsEvent.ToggleOrderSection)
                }) {
                    Icon(imageVector = Icons.Default.Sort,
                        contentDescription = "Sort",
                        tint = Color.Black)
                }
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.words) { word ->
                    WordItem(
                        word = word,
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {
                                    //navController.navigate(Screen.WordsScreen.route + "?id=${word.id}&dictionaryId=${word.dictionaryId}&dictionaryLanguage=${dictionaryLanguage}")
                                },
                                onLongClick = {
                                    viewModel.onEvent(WordsEvent.DeleteWord(word))
                                    scope.launch {
                                        val result = scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Dictionary deleted",
                                            actionLabel = "Undo"
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            viewModel.onEvent(WordsEvent.RestoreWord)
                                        }
                                    }
                                },
                            )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}