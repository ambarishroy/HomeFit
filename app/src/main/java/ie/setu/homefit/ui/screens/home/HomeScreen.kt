package ie.setu.homefit.ui.screens.home

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import ie.setu.homefit.R
import ie.setu.homefit.navigation.bottomAppBarDestinations
import ie.setu.homefit.navigation.userSignedOutDestinations
import ie.setu.homefit.ui.components.general.BottomAppBarProvider
import ie.setu.homefit.ui.components.general.TopAppBarProvider

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val exercises by homeViewModel.exercises.collectAsState()
    val userName by homeViewModel.name.collectAsState()
    val userEmail by homeViewModel.email.collectAsState()
    val isActiveSession = homeViewModel.isAuthenticated()
    val motivationalQuote = remember { "Stay fit, stay healthy!" }

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen =
        bottomAppBarDestinations.find { it.route == currentDestination?.route } ?: bottomAppBarDestinations.first()

    val userDestinations = if (!isActiveSession)
        userSignedOutDestinations
    else
        bottomAppBarDestinations

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarProvider(
                navController = navController,
                currentScreen = currentBottomScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                email = userEmail,
                name = userName,
                homeViewModel = homeViewModel
            ) { navController.navigateUp() }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Welcome to HomeFit, $userName!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = motivationalQuote,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(exercises) { exercise ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navController.navigate("exercise/$exercise")
                                },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(8.dp)
                                        .background(Color(0xFF00695c), shape = MaterialTheme.shapes.small),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.fitness),
                                        contentDescription = null,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = exercise,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF004d40)
                                )
                            }
                        }
                    }
                }

            }
        },
        bottomBar = {
            BottomAppBarProvider(
                navController = navController,
                currentScreen = currentBottomScreen,
                userDestinations = userDestinations
            )
        }
    )
}