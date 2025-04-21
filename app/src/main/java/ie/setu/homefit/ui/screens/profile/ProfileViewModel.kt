package ie.setu.homefit.ui.screens.profile

import android.net.Uri
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

    val displayName get() = authService.currentUser?.displayName.toString()
    val photoUri get() = authService.customPhotoUri
    val email get() = authService.email.toString()

    fun signOut() {
        viewModelScope.launch { authService.signOut() }
    }

    fun updatePhotoUri(uri: Uri) {
        viewModelScope.launch {
            authService.updatePhoto(uri)
            firestoreService.updatePhotoUris(email,photoUri!!)
        }
    }
    fun saveUserProfile(
        height: Int,
        weight: Int,
        targetCalories: Int,
        dob: String
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val profile = UserProfile(
            height = height,
            weight = weight,
            targetCaloriesPerWeek = targetCalories,
            dateOfBirth = dob,
            email = email
        )

        viewModelScope.launch {
            firestoreService.saveUserProfile(userId, profile)
        }
    }

}