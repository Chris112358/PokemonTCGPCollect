package com.example.pokemontcgpcollect.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemontcgpcollect.R
import com.example.pokemontcgpcollect.data.Dex
import com.example.pokemontcgpcollect.data.Packs
import com.example.pokemontcgpcollect.data.calcCompletion
import com.example.pokemontcgpcollect.data.calcNewCardInBooster
import com.example.pokemontcgpcollect.data.calcTotalCompletion
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
                Card(
                    elevation = CardDefaults.cardElevation( 4.dp ),
                    modifier = Modifier.padding(bottom = 6.dp),
                ) {
                    PackStatistics(
                        pack = pack,
                        dex = uiState.dex,
                        modifier = modifier)
                }
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
        modifier = modifier.padding(bottom = 2.dp)
    ) {
        Text(
            text = stringResource(pack.packId),
            modifier = modifier.padding(start = 4.dp))
        Row(
            modifier = modifier.padding(start = 3.dp, end = 4.dp)
        ) {
            Spacer(modifier = modifier.weight(1f))
            CollectedRarityText(
                packEntry = pack,
                modifier = modifier,
                )
        }

        Column(
            modifier = modifier.padding(start = 10.dp, end = 5.dp)
        ) {
            pack.booster.forEachIndexed{ index, booster ->
                TitleCompletenessBooster(
                    booster = booster,
                    dex = dex,
                    modifier = modifier.then(
                        if (index > 0) modifier.padding(top = 13.dp) else modifier.padding(top = 5.dp)
                    )
                )
                CompletenessBooster(
                    boosterId = booster.NameId,
                    dex = dex,
                    modifier = modifier,
                )
                NewCardBooster(
                    booster = booster,
                    dex = dex,
                    modifier = modifier
                )
            }
        }
    }
}


@Composable
fun TitleCompletenessBooster(
    booster: BoosterEntry,
    dex: List<DexEntry>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Text(stringResource(booster.NameId))
        Spacer(Modifier.weight(1.0f))
        Text(
            String.format(
                Locale.getDefault(),
                "%.1f",
                calcTotalCompletion(
                    dex = dex,
                    boosterId = booster.NameId,
                )
            ) + "%"
        )
    }
}


@Composable
fun NewCardBooster(
    booster: BoosterEntry,
    dex: List<DexEntry>,
    modifier: Modifier = Modifier,
) {
    Row {
        Text(stringResource(R.string.newCardPercent))
        //Spacer(modifier.weight(1.0f))
        Text(
            text =String.format(
                Locale.getDefault(),
                "%.3f",
                calcNewCardInBooster(
                    dex = dex,
                    boosterEntry = booster,
                ) 
            ) + "%",
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun CompletenessBooster(
    boosterId: Int,
    dex: List<DexEntry>,
    modifier: Modifier = Modifier,
) {
    val boosterCompleteness = calcCompletion(dex = dex, boosterId = boosterId)
    Column(
        modifier = modifier
    ) {
        Row {
            for (rarity in boosterCompleteness.keys.sorted()) {
                if (rarity < 10) {
                    Row() {
                        RarityIcon(rarity = rarity, modifier = modifier)
                        Text(
                            text = boosterCompleteness.getValue(rarity)[0].toString()
                                    + "/"
                                    + boosterCompleteness.getValue(rarity)[1].toString()
                        )
                    }
                }
            }
        }
        Row{
            for (rarity in boosterCompleteness.keys.sorted()) {
                if (rarity >= 10) {
                    Row() {
                        RarityIcon(rarity = rarity, modifier = modifier)
                        Text(
                            text = boosterCompleteness.getValue(rarity)[0].toString()
                                    + "/"
                                    + boosterCompleteness.getValue(rarity)[1].toString()
                        )
                    }
                }
            }
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