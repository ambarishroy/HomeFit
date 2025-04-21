package ie.setu.homefit.firebase.database

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import ie.setu.homefit.data.model.UserProfile
import ie.setu.homefit.data.rules.Constants.DONATION_COLLECTION
import ie.setu.homefit.data.rules.Constants.USER_EMAIL
import ie.setu.homefit.firebase.services.AuthService
import ie.setu.homefit.firebase.services.Fit
import ie.setu.homefit.firebase.services.FitList
import ie.setu.homefit.firebase.services.FirestoreService
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.Date
import javax.inject.Inject


class FirestoreRepository
@Inject constructor(private val auth: AuthService,
                    private val firestore: FirebaseFirestore
) : FirestoreService {
    override suspend fun saveUserProfile(userId: String, profile: UserProfile) {
        firestore.collection("users").document(userId).set(profile).await()
    }

    override suspend fun getAll(email: String): FitList {
        return firestore.collection(DONATION_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .dataObjects()
    }
    override suspend fun get(email: String,
                             donationId: String): Fit? {
        return firestore.collection(DONATION_COLLECTION)
            .document(donationId).get().await().toObject()
    }

    override suspend fun insert(email: String, donation: Fit) {
        val donationWithEmailAndImage =
            donation.copy(
                email = email,
                imageUri = auth.customPhotoUri!!.toString()
            )

        firestore.collection(DONATION_COLLECTION)
            .add(donationWithEmailAndImage)
            .await()
    }

    override suspend fun update(email: String,
                                donation: Fit) {
        val donationWithModifiedDate =
            donation.copy(dateModified = Date())

        firestore.collection(DONATION_COLLECTION)
            .document(donation._id)
            .set(donationWithModifiedDate).await()
    }

    override suspend fun delete(email: String,
                                donationId: String) {
        firestore.collection(DONATION_COLLECTION)
            .document(donationId)
            .delete().await()
    }

    override suspend fun updatePhotoUris(email: String, uri: Uri) {
        firestore.collection(DONATION_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    firestore.collection(DONATION_COLLECTION)
                        .document(document.id)
                        .update("imageUri", uri.toString())
                }
            }
            .addOnFailureListener { exception ->
                Timber.i("Error $exception")
            }
    }
}