package ie.setu.homefit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.homefit.ui.screens.login.LoginScreen
import ie.setu.homefit.ui.screens.register.RegisterScreen
//import ie.setu.homefit.ui.screens.home.HomeScreen
//import ie.setu.homefit.ui.screens.exercise.DailyExerciseScreen
//import ie.setu.homefit.ui.screens.profile.ProfileScreen

@Composable
fun NavHostProvider(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: AppDestination,
    paddingValues: PaddingValues,
    //permissions: Boolean
){
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = Modifier.padding(paddingValues = paddingValues))
     {
         composable(route = Login.route) {
             LoginScreen(
                 navController = navController,
                 onLogin = { navController.popBackStack() }
             )
         }
         composable(route = Register.route) {
             //call our 'Register' Screen Here
             RegisterScreen(
                 navController = navController,
                 onRegister = { navController.popBackStack() }
             )
         }
//         composable(route = Home.route) {
//             //call our 'Home' Screen Here
//             HomeScreen(modifier = modifier)
//         }
//        composable(Screen.Exercise.route) {
//            DailyExerciseScreen(navController)
//        }
//        composable(Screen.Profile.route) {
//            ProfileScreen(navController)
//        }
    }
}
