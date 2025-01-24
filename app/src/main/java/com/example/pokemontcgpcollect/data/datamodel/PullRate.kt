package com.example.pokemontcgpcollect.data.datamodel


data class PullRate(
    val boosterId: Int,
    val pullRate: List<Double>,
    val pullRateGodPack: List<Double>,
)
