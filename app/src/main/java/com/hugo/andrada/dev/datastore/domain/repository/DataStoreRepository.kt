package com.hugo.andrada.dev.datastore.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveDataStore(completed: Boolean)
    fun readSaveDataStore(): Flow<Boolean>

    suspend fun saveTime(time: Long)
    fun readTime(): Flow<Long>
}