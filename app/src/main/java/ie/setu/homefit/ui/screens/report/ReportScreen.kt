package ie.setu.homefit.ui.screens.report

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Your Health Report",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Height: $height cm",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Weight: $weight kg",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Your BMI: ${String.format("%.2f", bmi)}",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Calories Burned This Week",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Bar
        val target = targetCalories.toIntOrNull() ?: 1
        val progress = (caloriesBurned.toFloat() / target).coerceIn(0f, 1f)

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$caloriesBurned / $targetCalories kcal",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
