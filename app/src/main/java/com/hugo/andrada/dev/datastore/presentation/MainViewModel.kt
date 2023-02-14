package com.hugo.andrada.dev.datastore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.andrada.dev.datastore.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _stateCompleted = MutableStateFlow(false)
    val stateCompleted: StateFlow<Boolean> = _stateCompleted


    init {
        readDataStoreState()
    }


    fun saveDataStoreState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.saveDataStore(completed = completed)
        }
    }

    private fun readDataStoreState() {
        viewModelScope.launch(Dispatchers.IO) {
            _stateCompleted.value =
                useCases.readDataStore().stateIn(viewModelScope).value
        }
    }
}