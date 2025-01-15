package com.example.pokemontcgpcollect.data

import android.content.Context
import com.example.pokemontcgpcollect.data.database.DexDatabase
import com.example.pokemontcgpcollect.data.database.SaveStateRepo
import com.example.pokemontcgpcollect.data.database.SaveStateRepository

interface AppContainer {
    val saveStateRepo: SaveStateRepo
}

class AppDataContainer(private val context: Context): AppContainer {
    override val saveStateRepo: SaveStateRepo by lazy {
        SaveStateRepository(DexDatabase.getDatabase(context).dexStateDao())
    }
}