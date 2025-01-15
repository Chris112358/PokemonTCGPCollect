package com.example.pokemontcgpcollect.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemontcgpcollect.ui.theme.PokemonTCGPCollectTheme

@Composable
fun SettingsScreen(
    viewModel: CollectionViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val dexWidthOptions: List<Int> = listOf(3, 5, 7)

    val uiState by viewModel.uiState.collectAsState()
    val dexWidth = uiState.dexColumns

    Column {
        dexWidthOptions.forEach { item ->
            Row(
                modifier = modifier.selectable(
                    selected = item == uiState.dexColumns,
                    onClick = {viewModel.updateDexWidth(item)}
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = item == uiState.dexColumns,
                    onClick = {viewModel.updateDexWidth(item)}
                )
                Text(item.toString())
            }
        }
    }
}


@Preview
@Composable
fun SettingsPreview () {
    PokemonTCGPCollectTheme (darkTheme = false){
        val viewModel: CollectionViewModel = viewModel() //factory = AppViewModelProvider.Factory)
        // PackStatistics(packIndex = 0)
        SettingsScreen(viewModel=viewModel)
    }
}