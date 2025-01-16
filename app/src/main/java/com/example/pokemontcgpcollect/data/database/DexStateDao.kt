package com.example.pokemontcgpcollect.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokemontcgpcollect.data.datamodel.SaveStateDex
import kotlinx.coroutines.flow.Flow

@Dao
interface DexStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveState(item: SaveStateDex)

    @Update
    suspend fun updateState(item: SaveStateDex)

    @Delete
    suspend fun deleteState(item: SaveStateDex)

    @Query("SELECT * FROM saveStateDex ORDER BY cardId ASC")
    fun getAllSaved(): Flow<List<SaveStateDex>>

    @Query("SELECT * FROM saveStateDex WHERE cardID = :id LIMIT 1")
    fun getSavedId(id: Int): Flow<SaveStateDex?>?
}