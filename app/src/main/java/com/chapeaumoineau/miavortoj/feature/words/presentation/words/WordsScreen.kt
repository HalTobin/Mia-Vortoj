package com.chapeaumoineau.miavortoj.feature.words.presentation.words

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.Screen
import com.chapeaumoineau.miavortoj.feature.words.presentation.words.components.WordItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordsScreen(navController: NavController,
                viewModel:WordsViewModel = hiltViewModel()) {

    val state = viewModel.state.value

    val dictionaryId = viewModel.dictionaryId.value
    val dictionaryTitle = viewModel.title.value
    val dictionaryDescription = viewModel.description.value
    val dictionaryLanguageId = viewModel.dictionaryLanguageIso.value

    val color = viewModel.color.value
    val flag = viewModel.flag.value

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Screen.AddEditWordScreen.route + "?dictionaryId=${dictionaryId}&dictionaryLanguage=${dictionaryLanguageId}")
        }, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add word")
        }
    }, scaffoldState = scaffoldState) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color)) {
            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(flag),
                    contentDescription = "Flag",
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
                    Text(text = dictionaryTitle,
                        style = MaterialTheme.typography.h5,
                        color = Color.Black)
                    Text(text = dictionaryDescription,
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.Black,
                        maxLines = 3)
                }
                IconButton(onClick = {
                    //viewModel.onEvent(WordsEvent.ToggleOrderSection)
                }) {
                    Icon(imageVector = Icons.Default.Sort,
                        contentDescription = "Sort",
                        tint = Color.Black)
                }
            }

            Button(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(32.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.dark_green)),
                onClick = { navController.navigate(Screen.QuizScreen.route + "?dictionaryId=${dictionaryId}") },
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 8.dp,
                    end = 18.dp,
                    bottom = 8.dp
                )
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "Start a quiz",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.words_start_quiz), style = MaterialTheme.typography.subtitle1)
            }

            Divider(modifier = Modifier.padding(top = 8.dp), color = Color.Black, thickness = 1.dp)
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(state.words) { index, word ->
                    WordItem(
                        word = word,
                        isFromSource = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = { navController.navigate(Screen.WordCardScreen.route + "?wordId=${word.id}") },
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
                    if (index < state.words.lastIndex)
                        Divider(color = Color.Black, thickness = 1.dp)
                }
            }
        }
    }

}