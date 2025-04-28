package ie.setu.homefit.ui.screens.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.homefit.R
import ie.setu.homefit.data.model.ExerciseInfo
import ie.setu.homefit.firebase.services.AuthService
import ie.setu.homefit.firebase.services.FirestoreService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val authService: AuthService,
    private val firestoreService: FirestoreService
) : ViewModel() {

    private val _timerState = MutableStateFlow(0)
    val timerState: StateFlow<Int> = _timerState

    private val _caloriesBurned = MutableStateFlow(0)
    val caloriesBurned: StateFlow<Int> = _caloriesBurned

    private var timerJob: Job? = null

    fun startExercise() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                _timerState.value += 1
                _caloriesBurned.value = (_timerState.value / 6)
                saveCalories()
            }
        }
    }

    fun stopExercise() {
        timerJob?.cancel()
    }

    fun getExerciseInfo(exerciseName: String): ExerciseInfo {
        return when (exerciseName.lowercase()) {
            "push ups" -> ExerciseInfo(
                imageRes = R.drawable.aboutus_homer,
                instructions = "Keep your body straight, lower yourself until elbows at 90°."
            )
            "squats" -> ExerciseInfo(
                imageRes = R.drawable.aboutus_homer,
                instructions = "Stand tall, back straight, bend knees like sitting on a chair."
            )
            else -> ExerciseInfo(
                imageRes = R.drawable.aboutus_homer,
                instructions = "Follow correct form and pace yourself."
            )
        }
    }
    private fun saveCalories() {
        val userId = authService.currentUser?.uid ?: return
        val calories = _caloriesBurned.value

        viewModelScope.launch {
            firestoreService.saveCalories(userId, calories)
        }
    }
}
