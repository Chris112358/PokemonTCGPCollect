package com.example.pokemontcgpcollect.data.datamodel

import androidx.annotation.StringRes

data class PackEntry(
    val id: Int = 0,  //running number
    @StringRes val packId: Int,   //Name of Pack
    val booster: List<BoosterEntry>, //List of Booster available for this Pack
    var totalDiamond: Int = 10,
    var totalStar: Int = 10,
    var collectedDiamond: Int = 0,
    var collectedStar: Int = 0,
)
