package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditDictionaryScreen(navController: NavController,
                            dictionaryLanguage: Int,
                            viewModel: AddEditDictionaryViewModel = hiltViewModel()) {
    val titleState = viewModel.title.value
    val descriptionState = viewModel.description.value

    val languageIsoState = viewModel.dictionaryLanguageIso.value
    val colorState = viewModel.dictionaryLanguageColor.value
    val flagsState = viewModel.flags.value

    val scaffoldState = rememberScaffoldState()

    val dictionaryBackgroundAnimated = remember {
        Animatable(colorState)
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditDictionaryViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is AddEditDictionaryViewModel.UiEvent.SaveDictionary -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.onEvent(AddEditDictionaryEvent.SaveDictionary) }, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Default.Save, contentDescription = "Save dictionary")
        }
    },
        scaffoldState = scaffoldState) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(dictionaryBackgroundAnimated.value)
            .padding(16.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                for (c in 0..4) {
                    if(c!=4) {
                        val myLanguage = Language.getLanguageByIso(Language.DUMB_FAV[c])
                        Image(painter = painterResource(flagsState[c]),
                            contentDescription = "",
                            modifier = Modifier
                                .size(50.dp)
                                .shadow(15.dp, CircleShape)
                                .clip(CircleShape)
                                .border(
                                    width = 3.dp,
                                    color = if (viewModel.dictionaryLanguageIso.value == myLanguage.iso) {
                                        Color.Black
                                    } else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    scope.launch {
                                        dictionaryBackgroundAnimated.animateTo(
                                            targetValue = myLanguage.getColor(),
                                            animationSpec = tween(durationMillis = 500)
                                        )
                                    }
                                    viewModel.onEvent(
                                        AddEditDictionaryEvent.ChangeLanguage(
                                            myLanguage.iso
                                        )
                                    )
                                }
                        )
                    }
                    else Icon(imageVector = Icons.Default.MoreHoriz,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable {
                                viewModel.onEvent(AddEditDictionaryEvent.MoreLanguage)
                            }
                    )
                }

                /*
                Dictionary.languages.forEach { language ->
                    val languageIndex = Dictionary.languages.indexOf(language)
                    Image(painter = painterResource(Dictionary.flags_simple[languageIndex]),
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.dictionaryLanguage.value == language) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    dictionaryBackgroundAnimated.animateTo(
                                        targetValue = Dictionary.colors[languageIndex],
                                        animationSpec = tween(durationMillis = 500)
                                    )
                                }
                                viewModel.onEvent(
                                    AddEditDictionaryEvent.ChangeLanguage(
                                        languageIndex
                                    )
                                )
                            }
                    )
                }
                */
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(text = titleState.text,
                hint = stringResource(R.string.add_edit_dictionary_screen_title_hint),
                onValueChange = {
                    viewModel.onEvent(AddEditDictionaryEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditDictionaryEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h4)

            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(text = descriptionState.text,
                hint = stringResource(R.string.add_edit_dictionary_screen_description_hint),
                onValueChange = {
                    viewModel.onEvent(AddEditDictionaryEvent.EnteredDescription(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditDictionaryEvent.ChangeDescriptionFocus(it))
                },
                isHintVisible = descriptionState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxHeight())
        }    }
}