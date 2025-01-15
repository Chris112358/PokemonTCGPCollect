package com.example.pokemontcgpcollect.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokemontcgpcollect.ui.screens.CollectionViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            CollectionViewModel(
                saveStateRepo = collectionApplication().container.saveStateRepo,
                settingsRepo = collectionApplication().userSettingsRepository
            )
        }
    }
}

fun CreationExtras.collectionApplication(): CollectionApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CollectionApplication)