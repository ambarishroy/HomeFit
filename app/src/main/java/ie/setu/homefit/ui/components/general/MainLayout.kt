package ie.setu.homefit.ui.components.general

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ie.setu.homefit.navigation.AppDestination
import ie.setu.homefit.navigation.bottomAppBarDestinations
import ie.setu.homefit.ui.screens.home.HomeViewModel

@Composable
fun MainLayout(
    navController: NavHostController,
    currentScreen: AppDestination,
    homeViewModel: HomeViewModel,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarProvider(
                navController = navController,
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                email = "",
                name = "",
                homeViewModel = homeViewModel,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomAppBarProvider(
                navController = navController,
                currentScreen = currentScreen,
                userDestinations = bottomAppBarDestinations
            )
        }
    ) { padding ->
        content(padding)
    }
}
