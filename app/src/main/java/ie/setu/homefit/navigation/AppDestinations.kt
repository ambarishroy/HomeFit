package ie.setu.homefit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person

interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}
object Login : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.Login
    override val label = "Login"
    override val route = "login"
}
object Home : AppDestination {
    override val icon = Icons.Filled.Home
    override val label = "Home"
    override val route = "home"
}
object Register : AppDestination {
    override val icon = Icons.Default.AccountCircle
    override val label = "Register"
    override val route = "register"
}
object Profile : AppDestination {
    override val icon = Icons.Default.Person
    override val label = "Profile"
    override val route = "profile"
}
object About : AppDestination {
    override val icon = Icons.Filled.Info
    override val label = "About"
    override val route = "about"
}
val bottomAppBarDestinations = listOf(About, Profile)
val userSignedOutDestinations = listOf(Login, Register)
val allDestinations = listOf(
    Home, Login, Register)