package com.example.pokemontcgpcollect.data

import com.example.pokemontcgpcollect.R
import com.example.pokemontcgpcollect.data.datamodel.BoosterEntry


class Booster {
    fun loadBooster(): Map<Int, BoosterEntry> {
        return mapOf(
            R.string.booster_mewtwo to BoosterEntry(
                BoosterId = 1,
                NameId = R.string.booster_mewtwo,
                DrawId = R.drawable.p_a_010_mewtwo,
                totalCards = 100,
                collectedCards = 0,
                probabilityGodPack = 0.05,
            ),
            R.string.booster_charizard to BoosterEntry(
                BoosterId = 2,
                NameId = R.string.booster_charizard,
                DrawId = R.drawable.a1_280_charizard_ex,
                totalCards = 100,
                collectedCards = 0,
                probabilityGodPack = 0.05
            ),
            R.string.booster_pikachu to BoosterEntry(
                BoosterId = 3,
                NameId = R.string.booster_pikachu,
                DrawId = R.drawable.p_a_009_pikachu,
                totalCards = 100,
                collectedCards = 0,
                probabilityGodPack = 0.05
            ),
            R.string.booster_mew to BoosterEntry(
                BoosterId = 4,
                NameId = R.string.booster_mew,
                DrawId = R.drawable.a1a_077_mew_ex,
                totalCards = 100,
                collectedCards = 0,
                probabilityGodPack = 0.05
            ),
            R.string.booster_dialga to BoosterEntry(
                BoosterId = 5,
                NameId = R.string.booster_dialga,
                DrawId = R.drawable.a2_205_dialga_ex,
                totalCards = 100,
                collectedCards = 0,
                probabilityGodPack = 0.05
            ),
            R.string.booster_palkia to BoosterEntry(
                BoosterId = 6,
                NameId = R.string.booster_palkia,
                DrawId = R.drawable.a2_204_palkia_ex,
                totalCards = 100,
                collectedCards = 0,
                probabilityGodPack = 0.05
            )
        )
    }
}