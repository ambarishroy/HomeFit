package ie.setu.homefit.ui.screens.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.homefit.firebase.services.AuthService
import ie.setu.homefit.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
) : ViewModel() {

    var height = mutableStateOf("")
        private set
    var weight = mutableStateOf("")
        private set
    var targetCalories = mutableStateOf("")
        private set
    var dob = mutableStateOf("")
        private set
    var caloriesBurned = mutableStateOf(0)
        private set
    var bmi = mutableStateOf(0.0)

    fun loadUserProfile() {
        viewModelScope.launch {
            val userId = authService.currentUser?.uid
            if (userId != null) {
                val profile = firestoreService.getUserProfile(userId)
                profile?.let {
                    height.value = it.height.toString()
                    weight.value = it.weight.toString()
                    targetCalories.value = it.targetCaloriesPerWeek.toString()
                    dob.value = it.dateOfBirth
                    caloriesBurned.value = it.caloriesBurned

                    calculateBMI(it.height, it.weight)
                }
            }
        }
    }

    private fun calculateBMI(heightCm: Int, weightKg: Int) {
        if (heightCm > 0 && weightKg > 0) {
            val heightM = heightCm / 100.0
            bmi.value = weightKg / (heightM * heightM)
        } else {
            bmi.value = 0.0
        }
    }
}
