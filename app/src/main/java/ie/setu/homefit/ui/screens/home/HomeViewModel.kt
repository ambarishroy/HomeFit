package ie.setu.homefit.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.homefit.firebase.services.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {
    private val _exercises = MutableStateFlow<List<String>>(listOf(
        "Push Ups",
        "Squats",
        "Lunges",
        "Plank",
        "Burpees",
        "Mountain Climbers",
        "Sit Ups"
    ))
    val exercises: StateFlow<List<String>> = _exercises
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
    fun signOut() {
        viewModelScope.launch {
            authService.signOut()
        }
    }
}

