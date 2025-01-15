package com.example.pokemontcgpcollect.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SettingsRepository (
    private val dataStore: DataStore<Preferences>
){
    private companion object {
        val DEX_WIDTH = intPreferencesKey("dex_width")
        const val TAG = "SettingsRepo"
    }

    val dexWidthSetting: Flow<Int> = dataStore.data
        .catch{
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map{ preferences ->
            Log.d(TAG, preferences.toString())
            preferences[DEX_WIDTH] ?: 3
        }

    suspend fun saveDexWidth(newWidth: Int) {
        Log.d(TAG, "Saved Data")
        dataStore.edit { preferences ->
            preferences[DEX_WIDTH] = newWidth
        }
    }
}