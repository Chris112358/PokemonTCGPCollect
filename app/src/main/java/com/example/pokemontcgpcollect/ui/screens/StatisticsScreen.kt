package com.example.pokemontcgpcollect.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemontcgpcollect.data.Dex
import com.example.pokemontcgpcollect.data.Packs
import com.example.pokemontcgpcollect.data.calcNewCardInBooster
import com.example.pokemontcgpcollect.data.datamodel.BoosterEntry
import com.example.pokemontcgpcollect.data.datamodel.DexEntry
import com.example.pokemontcgpcollect.data.datamodel.DexUiState
import com.example.pokemontcgpcollect.data.datamodel.PackEntry
import com.example.pokemontcgpcollect.ui.theme.PokemonTCGPCollectTheme
import java.util.Locale


@Composable
fun StatisticsScreen(
    uiState: DexUiState,
    modifier: Modifier = Modifier,
) {
    Column {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 5.dp)
        ) {
            items(uiState.packs) { pack ->
                PackStatistics(pack, dex = uiState.dex)
            }
        }

    }

}


@Composable
fun PackStatistics(
    pack: PackEntry,
    dex: List<DexEntry>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(bottom = 20.dp)
    ) {
        Text(stringResource(pack.packId))
        Row {
            Spacer(modifier = modifier.weight(1f))
            CollectedRarityText(
                packEntry = pack,
                modifier = modifier,
                )
        }

        Column(
            modifier = modifier.padding(start = 10.dp, end = 5.dp)
        ) {
            for (booster in pack.booster) {
                CompletenessBooster(
                    booster = booster,
                    dex = dex,
                    modifier = modifier
                )
            }
        }


    }
}


@Composable
fun CompletenessBooster(
    booster: BoosterEntry,
    dex: List<DexEntry>,
    modifier: Modifier = Modifier,
) {
    Row {
        Text(stringResource(booster.NameId))
        Spacer(modifier.weight(1.0f))
        Text(
            String.format(
                Locale.getDefault(),
                "%.3f",
                calcNewCardInBooster(
                    dex = dex,
                    boosterEntry = booster,
                ) 
            ) + "%"
        )
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