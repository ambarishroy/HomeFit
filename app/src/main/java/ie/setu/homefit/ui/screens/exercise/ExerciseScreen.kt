package ie.setu.homefit.ui.screens.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExerciseScreen(
    exerciseName: String,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val timerState by viewModel.timerState.collectAsState()
    val caloriesBurned by viewModel.caloriesBurned.collectAsState()

    val exerciseInfo = viewModel.getExerciseInfo(exerciseName)
//
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Exercise Image
        Image(
            painter = painterResource(id = exerciseInfo.imageRes),
            contentDescription = exerciseName,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Exercise Instructions
        Text(
            text = exerciseInfo.instructions,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Timer
        Text(
            text = "Timer: ${timerState} sec",
            style = MaterialTheme.typography.headlineSmall
        )

        // Calories
        Text(
            text = "Calories Burned: $caloriesBurned kcal",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Start Button
        Button(
            onClick = { viewModel.startExercise() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Exercise")
        }
        // Stop Button
        Button(
            onClick = { viewModel.stopExercise() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Stop Exercise")
        }
    }
}
