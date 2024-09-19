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



}