package com.example.pokemontcgpcollect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokemontcgpcollect.ui.AppViewModelProvider
import com.example.pokemontcgpcollect.ui.screens.CollectionViewModel
import com.example.pokemontcgpcollect.ui.screens.DexScreen
import com.example.pokemontcgpcollect.ui.screens.MissionsScreen
import com.example.pokemontcgpcollect.ui.screens.SettingsScreen
import com.example.pokemontcgpcollect.ui.screens.StatisticsScreen
import com.example.pokemontcgpcollect.ui.theme.Bookmark_check
import com.example.pokemontcgpcollect.ui.theme.Playing_cards
import com.example.pokemontcgpcollect.ui.theme.PokemonTCGPCollectTheme
import com.example.pokemontcgpcollect.ui.theme.Query_stats

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewmodel: CollectionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = AppMenu.valueOf(
        backStackEntry?.destination?.route ?: AppMenu.Dex.name
    )
    Scaffold(
        topBar = {
            AppTopBar(
                currentScreen = currentScreen,
                modifier = modifier,
            )
        },
        bottomBar = {
            AppMenuBar(
                navController = navController,
                modifier = modifier
            )
        }
    ) { //it ->

        Box(modifier = modifier.padding(it)) {
            Navigator(
                navController = navController,
                viewModel = viewmodel,
                modifier = modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentScreen: AppMenu,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(currentScreen.title)
            )
        },
        modifier = modifier
    )
}


enum class AppMenu(
    var icon: ImageVector,
    var title: Int,
    var descriptor: Int,
) {
    Dex(
        title = R.string.dex_title,
        icon = Playing_cards,
        descriptor = R.string.dex_descriptor
    ),
    Settings(
        title = R.string.set_title,
        icon = Icons.Default.Settings,
        descriptor = R.string.set_descriptor
    ),
    Missions(
        title = R.string.mis_title,
        icon = Bookmark_check,
        descriptor = R.string.mis_descriptor
    ),
    Statistics(
        title = R.string.stats_title,
        icon = Query_stats,
        descriptor = R.string.stats_descriptor
    ),
}


@Composable
fun AppMenuBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val items = listOf(
        AppMenu.Dex,
        //AppMenu.Missions,
        AppMenu.Statistics,
        AppMenu.Settings,
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.descriptor)
                    )
                },
                label = { Text(text = stringResource(item.descriptor)) },
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    navController.navigate(item.name) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun Navigator(
    navController: NavHostController,
    viewModel: CollectionViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = AppMenu.Dex.name,
        modifier = modifier,
    ){
        composable(route = AppMenu.Dex.name) {
            DexScreen(
                uiState = uiState,
                onToggleClick = {item -> viewModel.toggleEntry(cardId = item)}
            )
        }
        composable(route = AppMenu.Settings.name) {
            SettingsScreen(
                uiState = uiState,
                onDexWidthClick = {item -> viewModel.updateDexWidth(item)} ,
            )
        }
        composable(route = AppMenu.Missions.name) {
            MissionsScreen()
        }
        composable(route = AppMenu.Statistics.name) {
            StatisticsScreen(
                uiState = uiState,
            )
        }
    }
}


@Preview
@Composable
fun DexPreview() {
    PokemonTCGPCollectTheme(darkTheme = true){
        MainScreen()
    }
}