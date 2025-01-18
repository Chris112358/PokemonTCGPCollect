package com.example.pokemontcgpcollect.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemontcgpcollect.R
import com.example.pokemontcgpcollect.data.datamodel.PackEntry
import com.example.pokemontcgpcollect.ui.theme.PokemonTCGPCollectTheme
import com.example.pokemontcgpcollect.ui.theme.SuitDiamond


@Composable
fun CollectedRarityText(
    packEntry: PackEntry,
    modifier: Modifier = Modifier,
) {
    val diamond:String = packEntry.collectedDiamond.toString() + "/" + packEntry.totalDiamond.toString()
    val star:String = packEntry.collectedStar.toString() + "/" + packEntry.totalStar.toString()

    Row {
        Spacer(modifier = modifier.weight(1f))
        Icon(
            imageVector = SuitDiamond,
            contentDescription = "Collected Diamond Rares",
            modifier.padding(end = 3.dp, top = 4.dp)
        )
        Text(
            text = diamond,
            modifier = modifier,
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Collected Star Rares",
            modifier.padding(start = 10.dp, end = 3.dp)
        )
        Text(
            text = star,
            modifier = modifier,
        )
    }
}


@Preview
@Composable
fun CommonPreview () {
    PokemonTCGPCollectTheme(darkTheme = true){
        //val viewModel: CollectionViewModel = viewModel()
        val packEntry = PackEntry(
            id = 0,
            packId = R.string.a1,
            totalDiamond = 100,
            totalStar = 50,
            collectedDiamond = 20,
            collectedStar = 10
        )

        CollectedRarityText(packEntry = packEntry)
    }
}