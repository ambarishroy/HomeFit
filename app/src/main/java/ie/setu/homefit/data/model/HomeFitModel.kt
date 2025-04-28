package ie.setu.homefit.data.model

import java.util.Date
import androidx.room.Entity
import com.google.firebase.firestore.DocumentId

@Entity
data class HomeFitModel(
    @DocumentId val _id: String = "N/A",
    val paymentType: String = "N/A",
    val paymentAmount: Int = 0,
    var message: String = "Go Homer!",
    val dateDonated: Date = Date(),
    val dateModified: Date = Date(),
    var email: String = "joe@bloggs.com",
    var imageUri: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)
data class UserProfile(
    var height: Int = 0,
    var weight: Int = 0,
    var targetCaloriesPerWeek: Int = 0,
    var dateOfBirth: String = "",
    var email: String = "",
    val caloriesBurned: Int = 0
)
data class Exercise(
    val id: Int,
    val name: String,
    val imageResId: Int,
    val description: String
)
data class ExerciseInfo(
    val imageRes: Int,
    val instructions: String
)

