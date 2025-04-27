package ie.setu.homefit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.homefit.ui.components.general.MainLayout
import ie.setu.homefit.ui.screens.about.AboutScreen
import ie.setu.homefit.ui.screens.exercise.ExerciseScreen
import ie.setu.homefit.ui.screens.home.HomeScreen
import ie.setu.homefit.ui.screens.home.HomeViewModel
import ie.setu.homefit.ui.screens.login.LoginScreen
import ie.setu.homefit.ui.screens.profile.ProfileScreen
import ie.setu.homefit.ui.screens.profile.ProfileViewModel
import ie.setu.homefit.ui.screens.register.RegisterScreen
import ie.setu.homefit.ui.screens.report.ReportScreen

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
                    navController.navigate(Profile.route) {
                        popUpTo(Register.route) { inclusive = true }
                    }
                }

            )
        }

        composable(route = Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val homeViewModel: HomeViewModel = hiltViewModel()
            MainLayout(
                navController = navController,
                currentScreen = Profile,
                homeViewModel = homeViewModel
            ) {
                ProfileScreen(
                    navController = navController,
                    profileViewModel = viewModel,
                    onSignOut = {
                        navController.navigate(Login.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    }
                )
            }
        }


        composable(route = Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            val homeViewModel: HomeViewModel = hiltViewModel()
            MainLayout(
                navController = navController,
                currentScreen = Home,
                homeViewModel = homeViewModel
            )  {
                HomeScreen(
                    homeViewModel = viewModel,
                    navController = navController
                )
            }
        }

        composable(route = About.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            MainLayout(
                navController = navController,
                currentScreen = About,
                homeViewModel = homeViewModel
            )  {
                AboutScreen()
            }
        }
        composable(route = Report.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            MainLayout(
                navController = navController,
                currentScreen = Report,
                homeViewModel = homeViewModel
            ) {
                ReportScreen()
            }
        }
        composable(
            route = "exercise/{exerciseName}"
        ) { backStackEntry ->
            val exerciseName = backStackEntry.arguments?.getString("exerciseName")
            val homeViewModel: HomeViewModel = hiltViewModel()
            MainLayout(
                navController = navController,
                currentScreen = Exercise,
                homeViewModel = homeViewModel
            ) {
                ExerciseScreen(exerciseName = exerciseName ?: "Unknown Exercise")
            }
        }




    }
}
