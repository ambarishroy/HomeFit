package ie.setu.homefit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.homefit.ui.screens.home.HomeScreenWrapper
import ie.setu.homefit.ui.screens.login.LoginScreen
import ie.setu.homefit.ui.screens.profile.ProfileScreen
import ie.setu.homefit.ui.screens.register.RegisterScreen
//import ie.setu.homefit.ui.screens.home.HomeScreen
//import ie.setu.homefit.ui.screens.exercise.DailyExerciseScreen
//import ie.setu.homefit.ui.screens.profile.ProfileScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: AppDestination = Login // defaulting to Login for now
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(route = Login.route) {
            LoginScreen(
                navController = navController,
                onLogin = {
                    navController.navigate(Home.route) {
                        popUpTo(Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Register.route) {
            RegisterScreen(
                navController = navController,
                onRegister = {
                    navController.navigate(Home.route) {
                        popUpTo(Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Profile.route) {
            ProfileScreen(
                onSignOut = {
                    navController.navigate(Login.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Home.route) {
            HomeScreenWrapper()
        }


    }
}
