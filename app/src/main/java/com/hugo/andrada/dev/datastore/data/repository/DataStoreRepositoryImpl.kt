package com.hugo.andrada.dev.datastore.data.repository

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hugo.andrada.dev.datastore.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val context: Application): DataStoreRepository {

    companion object PreferencesKey {
        const val PREFERENCES_NAME = "my_preferences"
        val mikeyBoolean = booleanPreferencesKey(name = "key_boolean")
        val miKeyTime = longPreferencesKey(name = "key_long")
        val miKeyName = stringPreferencesKey(name = "key_string")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        PREFERENCES_NAME
    )

    override suspend fun saveBoolean() {
        context.dataStore.edit { preferences ->
            val current = preferences[mikeyBoolean] ?: false
            preferences[mikeyBoolean] = !current
        }
    }

    override suspend fun saveTime(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[miKeyTime] = time
        }
    }

    override suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[miKeyName] = name
        }
    }

    override fun readTime(): Flow<Long> {
        return context.dataStore.data
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
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val state = preferences[mikeyBoolean] ?: false
                state
            }
    }

    override fun readName(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val state = preferences[miKeyName] ?: ""
                state
            }
    }
}