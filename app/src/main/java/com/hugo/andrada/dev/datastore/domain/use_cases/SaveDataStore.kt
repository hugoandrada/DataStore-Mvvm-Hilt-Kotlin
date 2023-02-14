package com.hugo.andrada.dev.datastore.domain.use_cases

import com.hugo.andrada.dev.datastore.data.repository.Repository

class SaveDataStore(
    private val repository: Repository
) {

    suspend operator fun invoke(completed: Boolean) {
        repository.saveData(completed = completed)
    }
}