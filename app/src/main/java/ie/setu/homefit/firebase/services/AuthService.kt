package ie.setu.homefit.firebase.services

import android.net.Uri
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import ie.setu.homefit.firebase.auth.Response

typealias FirebaseSignInResponse = Response<FirebaseUser>
typealias SignInWithGoogleResponse = Response<Boolean>

interface AuthService {
    val currentUserId: String
    val currentUser: FirebaseUser?
    val isUserAuthenticatedInFirebase: Boolean
    val email: String?
    val customPhotoUri: Uri?

    suspend fun authenticateUser(email: String, password: String)
            : FirebaseSignInResponse
    suspend fun createUser(name: String, email: String, password: String)
            : FirebaseSignInResponse
    suspend fun signOut()
    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential)
            : SignInWithGoogleResponse
    suspend fun authenticateGoogleUser(googleIdToken: String): FirebaseSignInResponse
    suspend fun updatePhoto(uri: Uri) : FirebaseSignInResponse
    suspend fun sendPasswordResetEmail(email: String): Response<Boolean>

}