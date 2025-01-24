package com.example.pokemontcgpcollect.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemontcgpcollect.R
import com.example.pokemontcgpcollect.data.datamodel.PackEntry
import com.example.pokemontcgpcollect.ui.theme.PokemonTCGPCollectTheme
import com.example.pokemontcgpcollect.ui.theme.SuitDiamond


@Composable
fun CollectedRarityText(
    packEntry: PackEntry,
    alignRight: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val diamond:String = packEntry.collectedDiamond.toString() + "/" + packEntry.totalDiamond.toString()
    val star:String = packEntry.collectedStar.toString() + "/" + packEntry.totalStar.toString()

    Row {
        if (alignRight) {
            Spacer(modifier = modifier.weight(1f))
        }
        Icon(
            imageVector = SuitDiamond,
            contentDescription = "Collected Diamond Rares",
            modifier.padding(end = 4.dp, top = 4.dp)
        )
        Text(
            text = diamond,
            modifier = modifier,
        )
        if (packEntry.totalStar > 0) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Collected Star Rares",
                modifier
                    .padding(start = 10.dp, end = 0.dp, )
                    .scale(0.75f)
            )
            Text(
                text = star,
                modifier = modifier,
            )
        }
    }
}


@Preview
@Composable
fun CommonPreview () {
    PokemonTCGPCollectTheme(darkTheme = true){
        val packEntry = PackEntry(
            id = 0,
            packId = R.string.a1,
            booster = listOf(),
            totalDiamond = 100,
            totalStar = 50,
            collectedDiamond = 20,
            collectedStar = 10
        )
        Box(Modifier.background(Color.White)) {
            CollectedRarityText(packEntry = packEntry)
        }
    }
}