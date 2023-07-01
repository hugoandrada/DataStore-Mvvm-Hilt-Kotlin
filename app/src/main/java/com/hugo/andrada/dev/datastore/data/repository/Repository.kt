package com.hugo.andrada.dev.datastore.data.repository

import com.hugo.andrada.dev.datastore.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataStore: DataStoreRepository
) {

    suspend fun saveData(completed: Boolean) {
        dataStore.saveDataStore(completed = completed)
    }

    fun readSaveData(): Flow<Boolean> {
        return dataStore.readSaveDataStore()
    }

    suspend fun saveTime(time: Long) {
        dataStore.saveTime(time)
    }

    fun readTime(): Flow<Long> {
        return dataStore.readTime()
    }
}