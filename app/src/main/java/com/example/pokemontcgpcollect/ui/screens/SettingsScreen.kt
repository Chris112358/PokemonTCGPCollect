package com.example.pokemontcgpcollect.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemontcgpcollect.data.Dex
import com.example.pokemontcgpcollect.data.Packs
import com.example.pokemontcgpcollect.data.datamodel.DexUiState
import com.example.pokemontcgpcollect.ui.theme.PokemonTCGPCollectTheme

@Composable
fun SettingsScreen(
    uiState: DexUiState,
    onDexWidthClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dexWidthOptions: List<Int> = listOf(3, 5, 7)
    Column {
        dexWidthOptions.forEach { item ->
            Row(
                modifier = modifier.selectable(
                    selected = item == uiState.dexColumns,
                    onClick = { onDexWidthClick(item) }//{viewModel.updateDexWidth(item)}
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = item == uiState.dexColumns,
                    onClick = { onDexWidthClick(item) }//{viewModel.updateDexWidth(item)}
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
        SettingsScreen(
            uiState = DexUiState(
                packs = Packs().loadPacks().toMutableList(),
                dex = Dex().loadDex().toMutableList(),
                dexColumns = 3
            ),
            onDexWidthClick = {},
        )
    }
}