package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Language
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.AddEditDictionaryEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_dictionary.AddEditDictionaryViewModel
import com.chapeaumoineau.miavortoj.feature.words.presentation.add_edit_word.AddEditWordEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.DictionariesEvent
import com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries.DictionariesViewModel

@Composable
fun DeleteDialog(isVisible: Boolean,
                 dictionary: Dictionary,
                 viewModel: DictionariesViewModel = hiltViewModel()) {
    if(isVisible) Dialog(onDismissRequest = { viewModel.onEvent(DictionariesEvent.DismissDeleteDialog) }){
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(8.dp),
            contentColor = MaterialTheme.colors.primary
        ) {
            Column(modifier = Modifier
                .background(Color.DarkGray)) {

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = stringResource(R.string.dialog_warning),
                        style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(16.dp))

                    /* TODO - Using regex would be better */

                    Text(text =
                    stringResource(R.string.dictionary_screen_delete_text_p1) + " "
                            + dictionary.title + " "
                            + stringResource(R.string.dictionary_screen_delete_text_p2) + " "
                            + stringResource(R.string.dictionary_screen_delete_to_enter),
                        style = MaterialTheme.typography.subtitle1)
                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(modifier = Modifier.fillMaxWidth(),
                        value = viewModel.deleteValidation.value,
                        onValueChange = {
                            viewModel.onEvent(DictionariesEvent.EnteredDeleteValidation(it))
                        },
                        label = {
                            Text(text = stringResource(R.string.dictionary_screen_delete_to_enter), color = Color.Red)
                        },
                        //colorText = TextFieldColors(textColor = Color.Red,),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Button(modifier = Modifier,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary),
                            onClick = {
                                viewModel.onEvent(DictionariesEvent.DismissDeleteDialog)
                            },
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                top = 8.dp,
                                end = 18.dp,
                                bottom = 8.dp
                            )
                        ) {
                            Text(text = stringResource(R.string.dialog_cancel),
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.Black)
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(modifier = Modifier,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor =Color.Red),
                            onClick = {
                                viewModel.onEvent(DictionariesEvent.DeleteDictionary(dictionary))
                            },
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                top = 8.dp,
                                end = 18.dp,
                                bottom = 8.dp
                            )
                        ) {
                            Text(text = stringResource(R.string.dialog_confirm),
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}
