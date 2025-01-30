package com.example.pokemontcgpcollect.data

import com.example.pokemontcgpcollect.R
import com.example.pokemontcgpcollect.data.datamodel.PackEntry

class Packs {
    fun loadPacks(): List<PackEntry> {
        val boosterEntries = Booster().loadBooster()
        return mutableListOf<PackEntry>(
            PackEntry(
                id = 1,
                packId = R.string.pack_A1,
                booster = listOf(
                    boosterEntries.getValue(R.string.booster_mewtwo),
                    boosterEntries.getValue(R.string.booster_charizard),
                    boosterEntries.getValue(R.string.booster_pikachu)
                ),
                totalDiamond = 226,
                totalStar = 100,
                collectedDiamond = 0,
                collectedStar = 0
            ),
            PackEntry(
                id = 2,
                packId = R.string.pack_A1a,
                booster = listOf(
                    boosterEntries.getValue(R.string.booster_mew)
                ),
                totalDiamond = 68,
                totalStar = 17,
                collectedDiamond = 0,
                collectedStar = 0
            ),
            PackEntry(
                id = 3,
                packId = R.string.pack_A2,
                booster = listOf(
                    boosterEntries.getValue(R.string.booster_dialga),
                    boosterEntries.getValue(R.string.booster_palkia)
                ),
                totalDiamond = 155,
                totalStar = 52,
                collectedDiamond = 0,
                collectedStar = 0
            ),
            PackEntry(
                id = 4,
                packId = R.string.pack_P_A,
                booster = listOf(),
                totalDiamond = 31,
                totalStar = 0,
                collectedDiamond = 31,
                collectedStar = 0
            )
        )
    }
}
