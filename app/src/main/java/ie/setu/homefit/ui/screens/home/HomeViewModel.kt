package ie.setu.homefit.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.homefit.firebase.services.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {

    private val _exercises = MutableStateFlow(listOf("Push Ups", "Squats", "Lunges", "Plank", "Burpees", "Mountain Climbers", "Sit Ups"))
    val exercises: StateFlow<List<String>> = _exercises.asStateFlow()

    private val _name = MutableStateFlow("User")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    val currentUser: FirebaseUser? get() = authService.currentUser

    init {
        currentUser?.let {
            _name.value = it.displayName ?: "User"
            _email.value = it.email ?: ""
        }
    }

    fun isAuthenticated() = authService.isUserAuthenticatedInFirebase

    fun signOut() {
        viewModelScope.launch {
            authService.signOut()
        }
    }
}