package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.miavortoj.R
import com.chapeaumoineau.miavortoj.feature.words.domain.model.Dictionary
import com.chapeaumoineau.miavortoj.feature.words.domain.use_case.DictionaryUseCases
import com.chapeaumoineau.miavortoj.feature.words.domain.util.DictionaryOrder
import com.chapeaumoineau.miavortoj.feature.words.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionariesViewModel @Inject constructor(private val dictionaryUseCases: DictionaryUseCases): ViewModel() {

    private val _state = mutableStateOf(DictionariesState())
    val state: State<DictionariesState> = _state

    private val _deleteValidation = mutableStateOf("")
    val deleteValidation: State<String> = _deleteValidation

    private var recentlyDeletedDictionary: Dictionary? = null

    private var getDictionariesJob: Job? = null

    init {
        getDictionaries(DictionaryOrder.Language(OrderType.Ascending))
    }


    fun onEvent(event: DictionariesEvent) {
        when(event) {
            is DictionariesEvent.Order -> {
                if(state.value.dictionaryOrder::class == event.dictionaryOrder::class && state.value.dictionaryOrder.orderType == event.dictionaryOrder.orderType) {
                    return
                }
                getDictionaries(event.dictionaryOrder)
            }
            is DictionariesEvent.DeleteDictionary -> {
                if(deleteValidation.value == state.value.deleteConfirmationTextToEnter) {
                    viewModelScope.launch {
                        dictionaryUseCases.deleteDictionary(event.dictionary)
                        recentlyDeletedDictionary = event.dictionary
                    }
                    _state.value = state.value.copy(isDeleteDialogVisible = false)
                    _deleteValidation.value = ""
                }

            }
            is DictionariesEvent.RestoreDictionary -> {
                viewModelScope.launch {
                    dictionaryUseCases.addDictionary(recentlyDeletedDictionary ?: return@launch)
                    recentlyDeletedDictionary = null
                }
            }
            is DictionariesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
            is DictionariesEvent.ToggleEditMode -> {
                _state.value = event.dictionaryId?.let { state.value.copy(dictionaryEdit = it) }!!
            }
            is DictionariesEvent.ToggleDeleteDialog -> {
                _state.value = state.value.copy(dictionaryEdit = -1)
                _state.value = state.value.copy(isDeleteDialogVisible = true)
                _state.value = state.value.copy(dictionaryDelete = event.dictionary)
                _state.value = state.value.copy(deleteConfirmationTextToEnter = event.deleteConfirmationToEnter)
            }
            is DictionariesEvent.EnteredDeleteValidation -> {
                _deleteValidation.value = event.deleteValidation
            }
            is DictionariesEvent.DismissDeleteDialog -> {
                _state.value = state.value.copy(isDeleteDialogVisible = false)
            }
        }
    }

    private fun getDictionaries(dictionaryOrder: DictionaryOrder) {
        getDictionariesJob?.cancel()
        getDictionariesJob = dictionaryUseCases.getDictionaries(dictionaryOrder).onEach {
                dictionaries -> _state.value = state.value.copy(dictionaries = dictionaries, dictionaryOrder = dictionaryOrder)
        }.launchIn(viewModelScope)
    }
}