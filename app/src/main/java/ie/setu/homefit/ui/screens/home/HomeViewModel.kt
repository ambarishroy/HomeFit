package ie.setu.homefit.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.homefit.firebase.services.AuthService
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {

    var name = mutableStateOf("")
    var email = mutableStateOf("")
    val currentUser: FirebaseUser?
        get() = authService.currentUser

    init {
        if (currentUser != null) {
            name.value = currentUser!!.displayName.toString()
            email.value = currentUser!!.email.toString()
        }
    }

    fun isAuthenticated() = authService.isUserAuthenticatedInFirebase
}

