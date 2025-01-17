package com.example.pokemontcgpcollect.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemontcgpcollect.data.datamodel.DexUiState
import com.example.pokemontcgpcollect.data.datamodel.PackEntry
import com.example.pokemontcgpcollect.ui.theme.PokemonTCGPCollectTheme
import com.example.pokemontcgpcollect.ui.theme.SuitDiamond


@Composable
fun StatisticsScreen(
    dexUiState: DexUiState,
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
            Icon(
                imageVector = SuitDiamond,
                contentDescription = null,
                modifier.padding(end = 3.dp, top = 4.dp)
            )
            Text(
                text = diamond
            )
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier.padding(start = 10.dp, end = 3.dp)
            )
            Text(
                text = star
            )
        }
    }
}

@Preview
@Composable
fun StatsPreview () {
    PokemonTCGPCollectTheme(darkTheme = false){
        val viewModel: CollectionViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()
        // PackStatistics(packIndex = 0)
        StatisticsScreen(dexUiState = uiState, viewModel=viewModel)
    }
}