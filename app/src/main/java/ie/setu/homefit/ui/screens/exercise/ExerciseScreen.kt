package ie.setu.homefit.ui.screens.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.homefit.R

@Composable
fun ExerciseScreen(
    exerciseName: String,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val timerState by viewModel.timerState.collectAsState()
    val caloriesBurned by viewModel.caloriesBurned.collectAsState()
    val isExerciseActive by remember { derivedStateOf { timerState > 0 } }
    val exerciseInfo = viewModel.getExerciseInfo(exerciseName)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = exerciseName,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF283593)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = exerciseInfo.imageRes),
                    contentDescription = exerciseName,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = exerciseInfo.instructions,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF424242),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Time Elapsed: ${timerState}s",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isExerciseActive) Color(0xFF283593) else Color.Gray
                )

                Text(
                    text = "Calories Burned: $caloriesBurned kcal",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF283593)
                )

                if (isExerciseActive) {
                    Text(
                        text = "Keep going! You're doing great!",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF424242),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                CircularProgressIndicator(
                    progress = (timerState % 60) / 60f,
                    modifier = Modifier.size(60.dp),
                    color = if (isExerciseActive) Color(0xFF283593) else Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.startExercise() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF283593),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.startexercise),
                    contentDescription = "Start"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Start")
            }

            Button(
                onClick = { viewModel.stopExercise() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB71C1C),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.stopexercise),
                    contentDescription = "Stop"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Stop")
            }
        }
    }
}
