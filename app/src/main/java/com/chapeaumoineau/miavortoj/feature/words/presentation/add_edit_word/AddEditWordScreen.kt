package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
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
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditWordScreen(navController: NavController,
                            viewModel: AddEditWordViewModel = hiltViewModel()) {
    val sourceState = viewModel.source.value
    val targetState = viewModel.target.value
    val notesState = viewModel.notes.value
    val colorState = viewModel.color.value

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
            TransparentHintTextField(text = sourceState.text,
                hint = stringResource(R.string.add_edit_word_source_hint),
                onValueChange = {
                    viewModel.onEvent(AddEditWordEvent.EnteredSource(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditWordEvent.ChangeSourceFocus(it))
                },
                isHintVisible = sourceState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.height(16.dp))

            Row() {
                Card(modifier = Modifier.weight(1f),
                    onClick = { viewModel.onEvent(AddEditWordEvent.MoreCategory) }) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row() {
                        Text(modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically),
                            text = categoryState.title)
                        Icon(modifier = Modifier.padding().align(Alignment.CenterVertically),
                            imageVector = ImageVector.vectorResource(categoryState.icon),
                            contentDescription = "")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
                TransparentHintTextField(text = targetState.text,
                    hint = stringResource(R.string.add_edit_word_target_hint),
                    onValueChange = {
                        viewModel.onEvent(AddEditWordEvent.EnteredTarget(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditWordEvent.ChangeTargetFocus(it))
                    },
                    isHintVisible = targetState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h6)
            }

            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(text = notesState.text,
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
                modifier = Modifier.fillMaxHeight())
        }
    }
}