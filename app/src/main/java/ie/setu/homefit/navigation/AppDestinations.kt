package ie.setu.homefit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Home

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