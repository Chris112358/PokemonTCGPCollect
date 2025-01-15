package com.example.pokemontcgpcollect.data.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saveStateDex")
data class SaveStateDex (
    @PrimaryKey(autoGenerate = false) val cardId: Int,
    var inPossession: Int = 0,
)