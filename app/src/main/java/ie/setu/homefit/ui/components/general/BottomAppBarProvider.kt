package ie.setu.homefit.ui.components.general

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ie.setu.homefit.navigation.AppDestination
import ie.setu.homefit.navigation.bottomAppBarDestinations
import ie.setu.homefit.ui.theme.HomeFitTheme
import timber.log.Timber

@Composable
fun BottomAppBarProvider(
    navController: NavHostController,
    currentScreen: AppDestination,
    userDestinations: List<AppDestination>
) {
    //initializing the default selected item
    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
    ) {
        //getting the list of bottom navigation items
        userDestinations.forEachIndexed { index, navigationItem ->
            //iterating all items with their respective indexes
            NavigationBarItem(
                selected = navigationItem == currentScreen,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = White,
                    unselectedIconColor = White,
                    unselectedTextColor = Black
                ),
                label = { Text(text = navigationItem.label) },
                icon = { Icon(navigationItem.icon, contentDescription = navigationItem.label) },
                onClick = {
                    Timber.tag("BottomAppBar").d("Clicked: ${navigationItem.route}")

                    navigationSelectedItem = index

                    val startDestination = runCatching {
                        navController.graph.findStartDestination().id
                    }.getOrNull()

                    val canNavigate = try {
                        navController.currentDestination != null
                    } catch (e: IllegalStateException) {
                        false
                    }
                    Timber.tag("BottomAppBar")
                        .d("canNavigate: $canNavigate, currentRoute: ${navController.currentDestination?.route}")
                    if (canNavigate) {
                        Timber.tag("BottomAppBar").d("Navigating to ${navigationItem.route}")
                        navController.navigate(navigationItem.route) {
                            if (startDestination != null) {
                                popUpTo(startDestination) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }


            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomAppBarScreenPreview() {
    HomeFitTheme  {
        BottomAppBarProvider(
            rememberNavController(),
            bottomAppBarDestinations.get(1),
            bottomAppBarDestinations
        )
    }
}