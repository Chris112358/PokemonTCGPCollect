package com.example.pokemontcgpcollect.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemontcgpcollect.R
import com.example.pokemontcgpcollect.data.datamodel.DexEntry
import com.example.pokemontcgpcollect.data.datamodel.PackEntry
import com.example.pokemontcgpcollect.ui.AppViewModelProvider
import com.example.pokemontcgpcollect.ui.theme.PokemonTCGPCollectTheme
import com.example.pokemontcgpcollect.ui.theme.SuitDiamond
import kotlinx.coroutines.launch
import java.util.Locale


private const val TAG: String = "DexScreenLog"

@Composable
fun DexScreen(
    //dexUiState: DexUiState,
    viewModel: CollectionViewModel = viewModel(),
    modifier : Modifier = Modifier,
    it: PaddingValues = PaddingValues(),
){
    val layoutDirection = LocalLayoutDirection.current
    //val uiState = remember { mutableStateOf(dexUiState) }
    val uiState by viewModel.uiState.collectAsState()
    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = it.calculateTopPadding())
            .padding(
                start = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateEndPadding(layoutDirection),
            )
    ) {
        PackList(
            //packs = uiState.totalDex,
            viewModel = viewModel,
            //numCols = uiState.dexWidthNumber,
            //uiState = uiState,
        )
    }
}

@Composable
fun DexCard(
    dexEntry: DexEntry,
    packId: Int,
    numCols: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val active: MutableState<Boolean> = remember { mutableStateOf(dexEntry.isActive) }

    val colorFilter: ColorFilter = if (active.value) {
        ColorFilter.colorMatrix(
            ColorMatrix().apply {
                setToSaturation(1f) // Set to black and white if inactive
            }
        )
    } else {
        ColorFilter.colorMatrix(
            ColorMatrix().apply {
                setToSaturation(0f) // Keeps image in color
            }
        )
    }

    Card(
        elevation = CardDefaults.cardElevation( 4.dp ),
        modifier = Modifier,
        onClick = {
            active.value = !active.value
            onClick()
        },
    ) {
        var fontSize: TextUnit = dimensionResource(id = R.dimen.font_big).value.sp
        var cardCornerRad: Dp = 6.dp
        if (numCols > 3) {
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
            cardCornerRad = 3.dp
        }
        if (numCols > 5) {
            fontSize = dimensionResource(id = R.dimen.font_small).value.sp
            cardCornerRad = 2.dp
        }
        Column (modifier = modifier){
            key(colorFilter) {
                Image(
                    painter = painterResource(dexEntry.imageResourceId),
                    contentDescription = stringResource(dexEntry.stringResourceID),
                    modifier = Modifier
                        .clip(RoundedCornerShape(cardCornerRad)),
                    contentScale = ContentScale.Fit,
                    colorFilter = colorFilter
                )
            }
            Row(modifier = modifier.fillMaxWidth()) {
                Text(
                    text = String.format(
                        locale = Locale.getDefault(),
                        "%03d",
                        dexEntry.dexNumbers[dexEntry.packIds.indexOf(packId)]),
                    fontSize = fontSize,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(2.dp),
                )
                Text(
                    text = LocalContext.current.getString(dexEntry.stringResourceID),
                    fontSize = fontSize,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),

                    //style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}


@Composable
fun PackList(
    //packs : List<PackWithDex>,
    //numCols: Int = 3,
    viewModel: CollectionViewModel,
    //uiState: DexUiState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsState()
    val numCols = uiState.dexColumns
    val packs = uiState.packs
    val dex = uiState.dex

    var itemPad: Dp = 8.dp
    if (numCols > 3) {
        itemPad = 5.dp
    }
    if (numCols > 5) {
        itemPad = 3.dp
    }
    LazyVerticalGrid(
        modifier = modifier
            .padding(horizontal = 5.dp, vertical = 5.dp),
        columns = GridCells.Fixed(numCols),
        verticalArrangement = Arrangement.spacedBy(itemPad),
        horizontalArrangement = Arrangement.spacedBy(itemPad),
    ) {
        for (pack in packs) {
            item(span = { GridItemSpan(numCols) }) {
                DexTitle(
                    pack = pack,
                    modifier = modifier,
                )
            }
            val packDex = dex
                .filter{ it.packIds.contains(pack.id) }
                .sortedBy { it.dexNumbers[it.packIds.indexOf(pack.id)] }
            items(packDex) { dexEntry ->

                DexCard(
                    dexEntry = dexEntry,
                    numCols = numCols,
                    //isActiveState = dexEntry.isActive,
                    onClick = {
                        //active = !active
                        Log.d(TAG, "calling ViewModel toggle")
                        viewModel.toggleEntry(cardId = dexEntry.cardId)
                        coroutineScope.launch {
                            viewModel.saveCard(cardId = dexEntry.cardId)
                        }
                    },
                    modifier = modifier,
                    packId = pack.id,
                )
            }
        }
    }
}

@Composable
fun DexTitle(
    pack: PackEntry,
    modifier: Modifier = Modifier
) {

    val diamond = (pack.collectedDiamond.toString()
            + "/"
            + pack.totalDiamond.toString())

    val star = (pack.collectedStar.toString()
            + "/"
            + pack.totalStar.toString())
    Row {
        Text(
            text = LocalContext.current.getString(pack.packId),
            textAlign = TextAlign.Start,
            modifier = modifier,
        )
        Spacer(modifier = modifier.weight(1f))
        Icon(
            imageVector = SuitDiamond,
            contentDescription = null,
            modifier.padding(end = 3.dp, top = 4.dp)
        )
        Text(
            text = diamond,
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            modifier.padding(start = 10.dp, end = 3.dp)
        )
        Text(
            text = star,
        )
    }
}


@Preview
@Composable
fun DexPreview() {
    PokemonTCGPCollectTheme (darkTheme = true){
        val viewModel: CollectionViewModel = viewModel(factory = AppViewModelProvider.Factory)
        val uiState by viewModel.uiState.collectAsState()
        DexScreen(
            //dexUiState = uiState,
            viewModel = viewModel,
            it = PaddingValues(all = 0.dp)
        )
    }
}