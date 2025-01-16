package com.example.pokemontcgpcollect.data.database

import com.example.pokemontcgpcollect.data.datamodel.SaveStateDex
import kotlinx.coroutines.flow.Flow

class SaveStateRepository(private val dexStateDao: DexStateDao): SaveStateRepo {
    override suspend fun saveState(item: SaveStateDex) {
        dexStateDao.saveState(item)
    }

    override suspend fun updateState(item: SaveStateDex) {
        dexStateDao.updateState(item)
    }

    override suspend fun deleteState(item: SaveStateDex) {
        dexStateDao.deleteState(item)
    }

    override fun getAllSaved(): Flow<List<SaveStateDex>> {
        return dexStateDao.getAllSaved()
    }

    override fun getSavedId(id: Int): Flow<SaveStateDex?>? {
        return dexStateDao.getSavedId(id)
    }
}