package com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.AddEditDictionaryEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word.AddEditWordEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word.AddEditWordViewModel

@Composable
fun CategoryDialog(isVisible: Boolean, viewModel: AddEditWordViewModel = hiltViewModel()) {
    if(isVisible) Dialog(onDismissRequest = { viewModel.onEvent(AddEditWordEvent.DismissCategoryDialog) }){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(value = viewModel.search.value,
                    onValueChange = {
                        viewModel.onEvent(AddEditWordEvent.EnteredSearch(it))
                    },
                    label = {
                        Text(text = stringResource(R.string.add_edit_dictionary_language_dialog_search_hint))
                    },
                    maxLines = 1
                )
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(viewModel.categories.value) { index, category ->
                    CategoryDialogItem(
                        category = category,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(
                                    AddEditWordEvent.OnNewCategorySelected(
                                        category.id
                                    )
                                )
                            }
                    )
                    if (index < Language.languagesList.lastIndex)
                        Divider(color = Color.Black, thickness = 1.dp)
                }
            }
        }
    }
}
