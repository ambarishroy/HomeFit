package ie.setu.homefit.firebase.storage

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import ie.setu.homefit.firebase.services.StorageService
import javax.inject.Inject

class StorageRepository @Inject
constructor(private val storage: FirebaseStorage
) : StorageService {

    override suspend fun uploadFile(uri: Uri, directory: String): Task<Uri> {
        val storageRef = storage.reference
        val imageRef = storageRef.child("$directory/${uri.lastPathSegment}")
        val uploadTask = imageRef.putFile(uri)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }
        return urlTask
    }
}