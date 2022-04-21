package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.components.LanguageDialog
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word.components.CategoryDialog
import com.chapeaumoineau.miavortoj.feature.words.presentation.components.TransparentHintTextField
import com.chapeaumoineau.miavortoj.feature.words.presentation.quiz.QuizEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditWordScreen(navController: NavController,
                            viewModel: AddEditWordViewModel = hiltViewModel()) {
    val sourceState = viewModel.source.value
    val targetState = viewModel.target.value
    val notesState = viewModel.notes.value
    val emoteState = viewModel.emote.value
    val colorState = viewModel.color.value

    val targetHintState = viewModel.targetHint.value

    val dialogState = viewModel.dialog.value
    val categoryState = viewModel.category.value

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditWordViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is AddEditWordViewModel.UiEvent.SaveWord -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(floatingActionButton = {
        CategoryDialog(isVisible = dialogState)
        FloatingActionButton(onClick = { viewModel.onEvent(AddEditWordEvent.SaveWord) }, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Default.Save, contentDescription = "Save dictionary")
        }
    },
        scaffoldState = scaffoldState) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(colorState)
            .padding(16.dp)) {

            Spacer(modifier = Modifier.height(16.dp))

            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
                value = sourceState,
                onValueChange = {
                    viewModel.onEvent(AddEditWordEvent.EnteredSource(it))
                },
                label = {
                    Text(text = stringResource(R.string.add_edit_word_source_hint), style = MaterialTheme.typography.h6)
                },
                //colors = TextFieldDefaults.textFieldColors(backgroundColor = textFieldBackgroundAnimated.value),
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
            )

            /*TransparentHintTextField(text = sourceState.text,
                hint = stringResource(R.string.add_edit_word_source_hint),
                onValueChange = {
                    viewModel.onEvent(AddEditWordEvent.EnteredSource(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditWordEvent.ChangeSourceFocus(it))
                },
                isHintVisible = sourceState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5)*/

            Spacer(modifier = Modifier.height(16.dp))

            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
                value = targetState,
                onValueChange = {
                    viewModel.onEvent(AddEditWordEvent.EnteredTarget(it))
                },
                label = {
                    Text(text = targetHintState, style = MaterialTheme.typography.h6)
                },
                //colors = TextFieldDefaults.textFieldColors(backgroundColor = textFieldBackgroundAnimated.value),
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier, Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {

                    TextField(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 32.dp),
                        value = emoteState,
                        onValueChange = {
                            viewModel.onEvent(AddEditWordEvent.EnteredEmote(it))
                        },
                        label = {
                            Text(text = stringResource(R.string.add_edit_word_emote_hint), style = MaterialTheme.typography.subtitle1)
                        },
                        //colors = TextFieldDefaults.textFieldColors(backgroundColor = textFieldBackgroundAnimated.value),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h6,
                    )

                    /*TransparentHintTextField(text = emoteState.text,
                        hint = stringResource(R.string.add_edit_word_emote_hint),
                        onValueChange = {
                            viewModel.onEvent(AddEditWordEvent.EnteredEmote(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(AddEditWordEvent.ChangeEmoteFocus(it))
                        },
                        isHintVisible = emoteState.isHintVisible,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h5)*/
                }
                Card(modifier = Modifier.weight(1f).padding(end = 16.dp).height(48.dp),
                    onClick = { viewModel.onEvent(AddEditWordEvent.MoreCategory) },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(Modifier, Arrangement.Center) {
                        Text(modifier = Modifier
                            .padding(end = 8.dp)
                            .align(Alignment.CenterVertically),
                            text = categoryState.title)
                        Icon(modifier = Modifier
                            .padding()
                            .align(Alignment.CenterVertically),
                            imageVector = ImageVector.vectorResource(categoryState.icon),
                            contentDescription = "")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            /*Box(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)) {
                TransparentHintTextField(text = targetState.text,
                    hint = targetHintState,
                    onValueChange = {
                        viewModel.onEvent(AddEditWordEvent.EnteredTarget(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditWordEvent.ChangeTargetFocus(it))
                    },
                    isHintVisible = targetState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h6)
            }*/

            Spacer(modifier = Modifier.height(16.dp))

            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .weight(1f),
                value = notesState,
                onValueChange = {
                    viewModel.onEvent(AddEditWordEvent.EnteredDescription(it))
                },
                label = {
                    Text(text = stringResource(R.string.add_edit_word_notes_hint), style = MaterialTheme.typography.subtitle1)
                },
                //colors = TextFieldDefaults.textFieldColors(backgroundColor = textFieldBackgroundAnimated.value),
                singleLine = false,
                textStyle = MaterialTheme.typography.subtitle1,
            )

            /*TransparentHintTextField(text = notesState.text,
                hint = stringResource(R.string.add_edit_dictionary_screen_description_hint),
                onValueChange = {
                    viewModel.onEvent(AddEditWordEvent.EnteredDescription(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditWordEvent.ChangeDescriptionFocus(it))
                },
                isHintVisible = notesState.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.subtitle1,
                //modifier = Modifier.fillMaxHeight()
            )*/

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(R.string.difficulty),
                style = MaterialTheme.typography.h6,
                color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Button(modifier = Modifier
                    //.align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(32.dp))
                    .border(
                        width = 4.dp,
                        color = if (viewModel.difficulty.value == 0) {
                            Color.Black
                        } else Color.Transparent,
                        shape = CircleShape
                    ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if(viewModel.difficulty.value == 0) colorResource(R.color.difficulty_easy_on)
                            else colorResource(R.color.difficulty_easy_off)),
                    onClick = {
                        viewModel.onEvent(AddEditWordEvent.ChangeDifficulty(0))
                    },
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 8.dp,
                        end = 18.dp,
                        bottom = 8.dp
                    )
                ) {
                    Text(text = stringResource(R.string.difficulty_easy),
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))

                Button(modifier = Modifier
                    //.align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(32.dp))
                    .border(
                        width = 4.dp,
                        color = if (viewModel.difficulty.value == 1) {
                            Color.Black
                        } else Color.Transparent,
                        shape = CircleShape
                    ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if(viewModel.difficulty.value == 1) colorResource(R.color.difficulty_medium_on)
                            else colorResource(R.color.difficulty_medium_off)),
                    onClick = {
                        viewModel.onEvent(AddEditWordEvent.ChangeDifficulty(1))
                    },
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 8.dp,
                        end = 18.dp,
                        bottom = 8.dp
                    )
                ) {
                    Text(text = stringResource(R.string.difficulty_medium),
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))

                Button(modifier = Modifier
                    //.align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(32.dp))
                    .border(
                        width = 4.dp,
                        color = if (viewModel.difficulty.value == 2) {
                            Color.Black
                        } else Color.Transparent,
                        shape = CircleShape
                    ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if(viewModel.difficulty.value == 2) colorResource(R.color.difficulty_hard_on)
                            else colorResource(R.color.difficulty_hard_off)),
                    onClick = {
                        viewModel.onEvent(AddEditWordEvent.ChangeDifficulty(2))
                    },
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 8.dp,
                        end = 18.dp,
                        bottom = 8.dp
                    )
                ) {
                    Text(text = stringResource(R.string.difficulty_hard),
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.Black)
                }
            }

        }
    }
}