package ie.setu.homefit.ui.screens.profile

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.homefit.data.model.UserProfile
import ie.setu.homefit.firebase.services.AuthService
import ie.setu.homefit.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val firestoreService: FirestoreService,

) : ViewModel() {
    val height = mutableStateOf("")
    val weight = mutableStateOf("")
    val targetCalories = mutableStateOf("")
    val dob = mutableStateOf("")

    val displayName get() = authService.currentUser?.displayName.toString()
    val photoUri get() = authService.customPhotoUri
    val email get() = authService.email.toString()
    var isProfileSaved = mutableStateOf(false)
        private set
    fun signOut() {
        viewModelScope.launch { authService.signOut() }
    }

    fun updatePhotoUri(uri: Uri) {
        viewModelScope.launch {
            authService.updatePhoto(uri)
            firestoreService.updatePhotoUris(email,photoUri!!)
        }
    }
    fun saveUserProfile() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val profile = UserProfile(
            height = height.value.toIntOrNull() ?: 0,
            weight = weight.value.toIntOrNull() ?: 0,
            targetCaloriesPerWeek = targetCalories.value.toIntOrNull() ?: 0,
            dateOfBirth = dob.value,
            email = email
        )

        viewModelScope.launch {
            try {
                isProfileSaved.value = false
                firestoreService.saveUserProfile(userId, profile)
                println("Profile saved, triggering navigation")
                isProfileSaved.value = true
            } catch (e: Exception) {
                println("Failed to save profile: ${e.message}")
            }
        }
    }

    fun loadUserProfile() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            val profile = firestoreService.getUserProfile(userId)
            profile?.let {
                height.value = it.height.toString()
                weight.value = it.weight.toString()
                dob.value = it.dateOfBirth
                targetCalories.value = it.targetCaloriesPerWeek.toString()
            }
        }
    }





}