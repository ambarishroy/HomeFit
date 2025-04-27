package ie.setu.homefit.ui.screens.home

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import ie.setu.homefit.navigation.Login
//import ie.setu.homefit.navigation.Report
import ie.setu.homefit.navigation.allDestinations
import ie.setu.homefit.navigation.bottomAppBarDestinations
import ie.setu.homefit.navigation.userSignedOutDestinations
import ie.setu.homefit.ui.components.general.BottomAppBarProvider
import ie.setu.homefit.ui.components.general.TopAppBarProvider
import ie.setu.homefit.ui.theme.HomeFitTheme
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               homeViewModel: HomeViewModel = hiltViewModel(),
               navController: NavHostController
) {
    val exercises = homeViewModel.exercises.collectAsState()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen =
        bottomAppBarDestinations.find { it.route == currentDestination?.route } ?: bottomAppBarDestinations.first()


    var startScreen = currentBottomScreen
    val currentUser = homeViewModel.currentUser
    val isActiveSession = homeViewModel.isAuthenticated()
    val userEmail = currentUser?.email ?: "Unknown"
    val userName = currentUser?.displayName ?: "User"

    val userDestinations = if (!isActiveSession)
        userSignedOutDestinations
    else
        bottomAppBarDestinations

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBarProvider(
            navController = navController,
            currentScreen = currentBottomScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            email = userEmail!!,
            name = userName!!,
            homeViewModel = homeViewModel,
        ) { navController.navigateUp() }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){
                Text(
                    text = "Welcome to HomeFit",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Select an Exercise:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn {
                    items(exercises.value) { exercise ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navController.navigate("exercise/${exercise}")
                                }
                        ) {
                            Text(
                                text = exercise,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
            androidx.compose.material3.Text(
                text = "Welcome to HomeFit!",
                modifier = Modifier.padding(paddingValues)
            )
        },
        bottomBar = {
            BottomAppBarProvider(
                navController,
                currentScreen = currentBottomScreen,
                userDestinations
            )
        }
    )

}
//@Composable
//fun HomeScreenWrapper() {
//    val viewModel: HomeViewModel = hiltViewModel()
//    val navController = rememberNavController()
//    HomeScreen(
//        homeViewModel = viewModel,
//        navController = navController
//    )
//}