package com.example.pokemontcgpcollect.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokemontcgpcollect.data.datamodel.SaveStateDex


@Database(entities = [SaveStateDex::class], version = 1, exportSchema = false)
abstract class DexDatabase: RoomDatabase() {
    abstract fun dexStateDao(): DexStateDao

    companion object {
        @Volatile
        private var Instance: DexDatabase? = null

        fun getDatabase(context: Context): DexDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    DexDatabase::class.java,
                    "saveStateDex"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}