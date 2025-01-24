package com.example.pokemontcgpcollect.data.datamodel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BoosterEntry (
    val BoosterId: Int,  //Id Number for Booster Pack
    @StringRes val NameId: Int,  // Name Id of Booster
    @DrawableRes val DrawId: Int,  // Id for Drawable of Booster
    var totalCards: Int,
    var collectedCards: Int,
    val probabilityGodPack: Double, // Probability to pull GodPack here
)
