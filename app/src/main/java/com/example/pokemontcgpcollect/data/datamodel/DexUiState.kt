package com.example.pokemontcgpcollect.data.datamodel

data class DexUiState (
    val packs: MutableList<PackEntry> = mutableListOf(),
    val dex: MutableList<DexEntry> = mutableListOf(),
    val dexColumns: Int = 3,
)