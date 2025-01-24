package com.example.pokemontcgpcollect.data

import com.example.pokemontcgpcollect.R
import com.example.pokemontcgpcollect.data.datamodel.BoosterEntry


class Booster {
    fun loadBooster(): Map<Int, BoosterEntry> {
        return mapOf(
            R.string.booster_mewtu to BoosterEntry(
                BoosterId = 1,
                NameId = R.string.booster_mewtu,
                DrawId = R.drawable.promo_a_010_mewtu,
                totalCards = 100,
                collectedCards = 0,
                probabilityGodPack = 0.05,
            ),
            R.string.booster_charizard to BoosterEntry(
                BoosterId = 2,
                NameId = R.string.booster_charizard,
                DrawId = R.drawable.unschlagbare_gene_280_glurak_ex,
                totalCards = 100,
                collectedCards = 0,
                probabilityGodPack = 0.05
            ),
            R.string.booster_pikachu to BoosterEntry(
                BoosterId = 3,
                NameId = R.string.booster_pikachu,
                DrawId = R.drawable.promo_a_009_pikachu,
                totalCards = 100,
                collectedCards = 0,
                probabilityGodPack = 0.05
            ),
            R.string.booster_mew to BoosterEntry(
                BoosterId = 4,
                NameId = R.string.booster_mew,
                DrawId = R.drawable.unschlagbare_gene_283_mew,
                totalCards = 100,
                collectedCards = 0,
                probabilityGodPack = 0.05
            )
        )
    }
}