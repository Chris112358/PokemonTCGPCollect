package com.example.pokemontcgpcollect.data

import com.example.pokemontcgpcollect.R
import com.example.pokemontcgpcollect.data.datamodel.PackEntry

class Packs {
    fun loadPacks(): List<PackEntry> {
        return mutableListOf<PackEntry>(
            PackEntry(
                id = 1,
                packId = R.string.a1,
                totalDiamond = 226,
                totalStar = 100,
                collectedDiamond = 0,
                collectedStar = 0
            ),
            PackEntry(
                id = 2,
                packId = R.string.a1a,
                totalDiamond = 68,
                totalStar = 17,
                collectedDiamond = 0,
                collectedStar = 0
            ),
            PackEntry(
                id = 3,
                packId = R.string.promoa,
                totalDiamond = 31,
                totalStar = 0,
                collectedDiamond = 31,
                collectedStar = 0
            )
        )
    }

}