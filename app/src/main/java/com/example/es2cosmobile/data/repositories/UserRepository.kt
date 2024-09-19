package com.example.es2cosmobile.data.repositories

import android.net.Uri
import com.example.es2cosmobile.data.database.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository {

    private val db: FirebaseFirestore = Firebase.firestore
    private val storage: FirebaseStorage = Firebase.storage

    suspend fun addUser(user: User): Boolean {
        return try {
            db.collection("user")
                .document(user.username)
                .set(user, SetOptions.merge())
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun checkLogin(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val documentSnapShot = db.collection("user")
                .document(username)
                .get()
                .await()
            if (documentSnapShot.exists()) {
                val storedPassword = documentSnapShot.getString("password")
                storedPassword == password
            } else {
                false
            }
        }
    }

    suspend fun checkUsername(username: String): Boolean {
        return withContext(Dispatchers.IO) {
            val documentSnapShot = db.collection("user")
                .document(username)
                .get()
                .await()
            !documentSnapShot.exists()
        }
    }


    suspend fun saveProfileImage(userId: String, imageUri: Uri): Boolean {
        return try {
            val storageRef = storage.reference
            val imageRef = storageRef.child("$userId/profile.jpg")

            imageRef.putFile(imageUri).await()

            val uri = imageRef.downloadUrl.await()
            db.collection("user")
                .document(userId)
                .update("urlFoto", uri.toString())
                .await()
            true

        } catch (e: Exception) {
            false
        }
    }

}