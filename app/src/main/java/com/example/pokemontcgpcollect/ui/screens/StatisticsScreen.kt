package com.example.pokemontcgpcollect.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemontcgpcollect.data.datamodel.PackEntry
import com.example.pokemontcgpcollect.ui.theme.PokemonTCGPCollectTheme


@Composable
fun StatisticsScreen(
    viewModel: CollectionViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
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
    val diamond:String = pack.collectedDiamond.toString() + "/" + pack.totalDiamond.toString()
    val star:String = pack.collectedStar.toString() + "/" + pack.totalStar.toString()

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
        val viewModel: CollectionViewModel = viewModel()
        //val uiState by viewModel.uiState.collectAsState()
        // PackStatistics(packIndex = 0)
        StatisticsScreen(viewModel=viewModel)
    }
}