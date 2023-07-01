package com.hugo.andrada.dev.datastore.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hugo.andrada.dev.datastore.core.Constants.DATA_STORE_KEY
import com.hugo.andrada.dev.datastore.core.Constants.DATA_STORE_NAME
import com.hugo.andrada.dev.datastore.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStoreRepositoryImpl(context: Context): DataStoreRepository {

    companion object PreferencesKey {
        val mikey = booleanPreferencesKey(name = DATA_STORE_KEY)
        val miKeyTime = longPreferencesKey(name = "key_time")
    }

    private val dataStore = context.dataStore

    override suspend fun saveDataStore(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[mikey] = completed
        }
    }

    override suspend fun saveTime(time: Long) {
        dataStore.edit { preferences ->
            preferences[miKeyTime] = time
        }
    }

    override fun readTime(): Flow<Long> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val state = preferences[miKeyTime] ?: 0L
                state
            }
    }

    override fun readSaveDataStore(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val state = preferences[mikey] ?: false
                state
            }
    }
}