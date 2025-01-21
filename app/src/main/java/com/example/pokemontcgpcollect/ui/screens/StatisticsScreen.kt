package com.example.pokemontcgpcollect.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemontcgpcollect.data.Dex
import com.example.pokemontcgpcollect.data.Packs
import com.example.pokemontcgpcollect.data.datamodel.DexUiState
import com.example.pokemontcgpcollect.data.datamodel.PackEntry
import com.example.pokemontcgpcollect.ui.theme.PokemonTCGPCollectTheme


@Composable
fun StatisticsScreen(
    uiState: DexUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(uiState.packs) { pack ->
            PackStatistics(pack)
        }
    }

}


@Composable
fun PackStatistics(
    pack: PackEntry,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(stringResource(pack.packId))
        Row {
            Spacer(modifier = modifier.weight(1f))
            CollectedRarityText(
                packEntry = pack,
                modifier = modifier,
                )
        }
    }
}

@Preview
@Composable
fun StatsPreview () {
    PokemonTCGPCollectTheme(darkTheme = false){
        StatisticsScreen(
            uiState = DexUiState(
                packs = Packs().loadPacks().toMutableList(),
                dex = Dex().loadDex().toMutableList(),
                dexColumns = 3
            ),
            modifier = Modifier,
        )
    }
}