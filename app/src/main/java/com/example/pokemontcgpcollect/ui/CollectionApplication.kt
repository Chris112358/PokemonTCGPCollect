package com.example.pokemontcgpcollect.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.pokemontcgpcollect.data.AppContainer
import com.example.pokemontcgpcollect.data.AppDataContainer
import com.example.pokemontcgpcollect.data.datastore.SettingsRepository


private const val SETTINGS = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SETTINGS
)


class CollectionApplication : Application() {
    lateinit var container: AppContainer
    lateinit var userSettingsRepository: SettingsRepository

    override fun onCreate() {
        Log.d("Log", "Test1")
        super.onCreate()
        Log.d("Log", "Test")
        container = AppDataContainer(this)
        userSettingsRepository = SettingsRepository(dataStore)
    }
}