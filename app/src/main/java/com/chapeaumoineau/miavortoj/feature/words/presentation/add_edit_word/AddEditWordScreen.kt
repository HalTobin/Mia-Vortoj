package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.presentation.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditWordScreen(navController: NavController,
                            viewModel: AddEditWordViewModel = hiltViewModel()) {
    val sourceState = viewModel.source.value
    val targetState = viewModel.target.value
    val notesState = viewModel.notes.value
    val colorState = viewModel.color.value

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
        FloatingActionButton(onClick = { viewModel.onEvent(AddEditWordEvent.SaveDictionary) }, backgroundColor = MaterialTheme.colors.primary) {
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
                singleLine = true,
                textStyle = MaterialTheme.typography.subtitle1,
                modifier = Modifier.fillMaxHeight())
        }    }
}