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
    var email: String = ""
)

