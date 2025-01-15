package com.example.pokemontcgpcollect.data.database

import com.example.pokemontcgpcollect.data.datamodel.SaveStateDex
import kotlinx.coroutines.flow.Flow

interface SaveStateRepo {
    suspend fun saveState(item: SaveStateDex)

    suspend fun updateState(item: SaveStateDex)

    suspend fun deleteState(item: SaveStateDex)

    fun getAllSaved(): Flow<List<SaveStateDex>>

    fun getSavedId(id: Int): Flow<SaveStateDex>?
}