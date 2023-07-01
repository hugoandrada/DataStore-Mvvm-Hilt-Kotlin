package com.hugo.andrada.dev.datastore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.andrada.dev.datastore.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _stateCompleted = MutableStateFlow(false)
    val stateCompleted: StateFlow<Boolean> = _stateCompleted

    private val _time = MutableStateFlow<Long>(0)
    val time = _time.asStateFlow()


    init {
        readDataStoreState()
        readTimeDataStore()
    }


    fun saveDataStoreState(completed: Boolean) {
        viewModelScope.launch {
            repository.saveData(completed)
        }
    }

    fun saveTimeDataStore(time: Long) {
        viewModelScope.launch {
            repository.saveTime(time)
        }
    }

    private fun readDataStoreState() {
        repository.readSaveData().onEach { isData ->
            _stateCompleted.value = isData
        }.launchIn(viewModelScope)
    }

    private fun readTimeDataStore() {
        repository.readTime().onEach { savedTime ->
            _time.value = savedTime
        }.launchIn(viewModelScope)
    }
}