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
                imageRes = R.drawable.pushups,
                instructions = """
                1. Start in a plank position with your hands shoulder-width apart.
                2. Keep your body in a straight line from head to heels.
                3. Lower your body by bending your elbows until your chest almost touches the floor.
                4. Push back up to the starting position.
                5. Keep your core tight and avoid arching your back.
            """.trimIndent()
            )
            "squats" -> ExerciseInfo(
                imageRes = R.drawable.squat,
                instructions = """
                1. Stand with feet shoulder-width apart.
                2. Lower your body as if sitting back into a chair.
                3. Keep your chest up and your back straight.
                4. Go down until your thighs are parallel to the ground.
                5. Push through your heels to stand back up.
            """.trimIndent()
            )
            "lunges" -> ExerciseInfo(
                imageRes = R.drawable.lunges,
                instructions = """
                1. Stand upright with your feet together.
                2. Step forward with one leg and lower your hips until both knees are at about 90-degree angles.
                3. Keep your back straight and your front knee over your ankle.
                4. Push through the front heel to return to the starting position.
                5. Repeat with the opposite leg.
            """.trimIndent()
            )
            "plank" -> ExerciseInfo(
                imageRes = R.drawable.planks,
                instructions = """
                1. Get into a push-up position but rest on your forearms.
                2. Keep your body in a straight line from head to heels.
                3. Engage your core and hold the position without sagging.
                4. Avoid lifting your hips too high or letting them sink.
                5. Hold for as long as you can.
            """.trimIndent()
            )
            "burpees" -> ExerciseInfo(
                imageRes = R.drawable.burpees,
                instructions = """
                1. Stand with your feet shoulder-width apart.
                2. Lower into a squat position and place your hands on the floor.
                3. Kick your feet back into a plank position.
                4. Perform a push-up, then jump your feet back to your hands.
                5. Jump up explosively with your arms overhead.
            """.trimIndent()
            )
            "jumping jacks" -> ExerciseInfo(
                imageRes = R.drawable.jumpingjacks,
                instructions = """
                1. Stand with your feet together and arms at your sides.
                2. Jump your feet out to the sides while simultaneously raising your arms overhead.
                3. Jump back to the starting position with your feet together and arms at your sides.
                4. Repeat in a rhythmic, continuous motion.
                5. Keep a steady pace and maintain good posture.
            """.trimIndent()
            )
            "sit ups" -> ExerciseInfo(
                imageRes = R.drawable.situps,
                instructions = """
                1. Lie on your back with your knees bent and feet flat on the floor.
                2. Place your hands behind your head or cross them on your chest.
                3. Tighten your core and lift your torso toward your thighs.
                4. Lower back down slowly to the starting position.
                5. Keep your feet firmly planted and avoid using momentum.
            """.trimIndent()
            )
            else -> ExerciseInfo(
                imageRes = R.drawable.fitness,
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
