package ie.setu.homefit.firebase.services

import android.net.Uri
import ie.setu.homefit.data.model.HomeFitModel
import ie.setu.homefit.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

typealias Fit = HomeFitModel
typealias FitList = Flow<List<Fit>>

interface FirestoreService {

    suspend fun getAll(email: String) : FitList
    suspend fun get(email: String, donationId: String) : Fit?
    suspend fun insert(email: String, donation: Fit)
    suspend fun update(email: String, donation: Fit)
    suspend fun delete(email: String, donationId: String)
    suspend fun updatePhotoUris(email: String, uri: Uri)
    suspend fun saveUserProfile(userId: String, profile: UserProfile)
    suspend fun getUserProfile(userId: String): UserProfile?
    suspend fun saveCalories(userId: String, calories: Int)


}