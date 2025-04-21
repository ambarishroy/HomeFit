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
    val height: Int,
    val weight: Int,
    val targetCaloriesPerWeek: Int,
    val dateOfBirth: String,
    val email: String
)
