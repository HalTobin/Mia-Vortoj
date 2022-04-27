package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.components.LanguageDialog
import com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.components.DeleteDialog
import com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.components.DictionaryItem
import com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.components.OrderDictionariesSection
import com.chapeaumoineau.miavortoj.feature.words.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun DictionariesScreen(navController: NavController, viewModel:DictionariesViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val deleteTextToEnter = stringResource(R.string.dictionary_screen_delete_to_enter)

    BackHandler(enabled = (state.dictionaryEdit != -1)) {
        if(state.dictionaryEdit!=-1) viewModel.onEvent(DictionariesEvent.ToggleEditMode(-1))
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Screen.AddEditDictionaryScreen.route)
        }, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add dictionary")
        }
    }, scaffoldState = scaffoldState) {
        state.dictionaryDelete?.let { it1 ->
            DeleteDialog(isVisible = state.isDeleteDialogVisible, it1)
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.dictionary_screen_title),
                    style = MaterialTheme.typography.h4)
                IconButton(onClick = {
                    viewModel.onEvent(DictionariesEvent.ToggleOrderSection)
                }) {
                    Icon(imageVector = Icons.Default.Sort,
                        contentDescription = "Sort")
                }
            }

            AnimatedVisibility(visible = state.isOrderSectionVisible, enter = fadeIn() + slideInVertically(), exit = fadeOut() + slideOutVertically()) {
                OrderDictionariesSection(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp), dictionaryOrder = state.dictionaryOrder, onOrderChange = {
                    viewModel.onEvent(DictionariesEvent.Order(it))
                })
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.dictionaries) { dictionary ->
                    DictionaryItem(
                        dictionary = dictionary,
                        dictionaryEdited = state.dictionaryEdit,
                        onMoreClick = {
                            viewModel.onEvent(DictionariesEvent.ToggleEditMode(dictionary.id))

                        },
                        onEditClick = {
                            viewModel.onEvent(DictionariesEvent.ToggleEditMode(-1))
                            navController.navigate(Screen.AddEditDictionaryScreen.route + "?dictionaryId=${dictionary.id}")
                        },
                        onDeleteClick = {
                            viewModel.onEvent(DictionariesEvent.ToggleDeleteDialog(dictionary, deleteTextToEnter))
                        },
                        onBackClick = {
                            viewModel.onEvent(DictionariesEvent.ToggleEditMode(-1))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    viewModel.onEvent(DictionariesEvent.ToggleEditMode(-1))
                                    navController.navigate(Screen.WordsScreen.route + "?dictionaryId=${dictionary.id}")
                                }
                            )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}