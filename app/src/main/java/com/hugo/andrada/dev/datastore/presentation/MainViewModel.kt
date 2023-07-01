package com.hugo.andrada.dev.datastore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.andrada.dev.datastore.domain.repository.DataStoreRepository
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
    private val repository: DataStoreRepository
): ViewModel() {

    private val _stateCompleted = MutableStateFlow(false)
    val stateCompleted: StateFlow<Boolean> = _stateCompleted

    private val _time = MutableStateFlow<Long>(0)
    val time = _time.asStateFlow()

    private val _name = MutableStateFlow<String>("")
    val name = _name.asStateFlow()


    init {
        readDataStoreState()
        readTimeDataStore()
        readNameDataStore()
    }


    fun saveDataStoreState(completed: Boolean) {
        viewModelScope.launch {
            repository.saveDataStore(completed)
        }
    }

    fun saveTimeDataStore(time: Long) {
        viewModelScope.launch {
            repository.saveTime(time)
        }
    }

    fun saveNameDataStore(name: String) {
        viewModelScope.launch {
            repository.saveName(name)
        }
    }

    private fun readDataStoreState() {
        repository.readSaveDataStore().onEach { isData ->
            _stateCompleted.value = isData
        }.launchIn(viewModelScope)
    }

    private fun readTimeDataStore() {
        repository.readTime().onEach { savedTime ->
            _time.value = savedTime
        }.launchIn(viewModelScope)
    }

    private fun readNameDataStore() {
        repository.readName().onEach { saveName ->
            _name.value = saveName
        }.launchIn(viewModelScope)
    }
}