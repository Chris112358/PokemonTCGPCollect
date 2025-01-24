package com.example.pokemontcgpcollect.data.datamodel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes



data class DexEntry(
    val cardId: Int, // number of card (Independent of Dex)
    @StringRes val stringResourceID: Int,  //Name of Card
    @DrawableRes val imageResourceId: Int,  //Image of Card
    val dexNumbers: List<Int>,   // list of Dex numbers in Pack
    val packIds: List<Int>,  // List of Ids of Pack Name of Pack (eg A1 or A2), same index as dexNumbers, corresponds to PackEntry.id
    val boosterId: List<Int>, // List ID of packs to find card (eg Mewtwo, Pikachu... as R.string values)
    var isActive: Boolean = false, // Is Card in possession
    var numberPossession: Int = 0, // number in possession
    val rarity: Int, // 1, 2, 3, 4 for Diamond Rare, 11, 12, 12 for star rare, 20 for crown rare
    val pullRates: Map<Int, PullRate>,  //Map from boosterId to PullRate, for every boosterid in boosterId there should be a map here
)