package com.chapeaumoineau.miavortoj.feature.words.presentation.dictionnaries

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                viewModelScope.launch {
                    dictionaryUseCases.deleteDictionary(event.dictionary)
                    recentlyDeletedDictionary = event.dictionary
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
                _state.value = event.dictionary.id?.let { state.value.copy(dictionaryEdit = it) }!!
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