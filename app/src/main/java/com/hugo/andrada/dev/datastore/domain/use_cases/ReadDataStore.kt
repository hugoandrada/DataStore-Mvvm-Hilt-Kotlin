package com.hugo.andrada.dev.datastore.domain.use_cases

import com.hugo.andrada.dev.datastore.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadDataStore(
    private val repository: Repository
) {

    operator fun invoke(): Flow<Boolean> {
        return repository.readSaveData()
    }
}