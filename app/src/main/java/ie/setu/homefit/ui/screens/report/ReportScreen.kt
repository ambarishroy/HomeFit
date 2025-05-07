package ie.setu.homefit.ui.screens.report

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ReportScreen(
    reportViewModel: ReportViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        reportViewModel.loadUserProfile()
    }
    val height = reportViewModel.height.value
    val weight = reportViewModel.weight.value
    val targetCalories = reportViewModel.targetCalories.value
    val caloriesBurned = reportViewModel.caloriesBurned.value
    val bmi = reportViewModel.bmi.value

    val target = targetCalories.toIntOrNull() ?: 1
    val progress = (caloriesBurned.toFloat() / target).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your Health Report",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Health Information Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Personal Information",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00695C)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Height: $height cm",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Weight: $weight kg",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BMI Card
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
                val bmiCategory = when {
                    bmi < 18.5 -> "Underweight"
                    bmi in 18.5..24.9 -> "Normal"
                    bmi in 25.0..29.9 -> "Overweight"
                    else -> "Obese"
                }

                Text(
                    text = "BMI: ${String.format("%.2f", bmi)} ($bmiCategory)",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = when (bmiCategory) {
                        "Underweight" -> Color(0xFF1976D2)
                        "Normal" -> Color(0xFF388E3C)
                        "Overweight" -> Color(0xFFFBC02D)
                        else -> Color(0xFFD32F2F)
                    }
                )
                Text(
                    text = getHealthTip(bmiCategory),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF424242)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Calories Burned Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Calories Burned",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )

                Spacer(modifier = Modifier.height(16.dp))

                CircularProgressIndicator(
                    progress = progress,
                    modifier = Modifier.size(100.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$caloriesBurned / $targetCalories kcal",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = getMotivationalMessage(progress),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF424242)
                )
            }
        }
    }
}

fun getHealthTip(bmiCategory: String): String {
    return when (bmiCategory) {
        "Underweight" -> "Consider eating nutrient-rich foods to gain weight."
        "Normal" -> "Great job! Maintain a balanced diet and regular exercise."
        "Overweight" -> "Focus on a healthy diet and regular physical activity."
        "Obese" -> "Consult a healthcare professional for a personalized plan."
        else -> "Maintain a balanced lifestyle."
    }
}

fun getMotivationalMessage(progress: Float): String {
    return when {
        progress >= 1f -> "Great job! You’ve hit your target this week!"
        progress >= 0.75f -> "Almost there! Keep pushing!"
        progress >= 0.5f -> "You’re halfway there! Stay consistent!"
        else -> "Keep moving! Every step counts!"
    }
}
