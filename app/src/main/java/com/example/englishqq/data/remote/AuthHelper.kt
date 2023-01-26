package com.example.englishqq.data.remote

import com.example.englishqq.data.model.UserData
import com.example.englishqq.utils.N
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthHelper(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {

    fun signUp(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun addUserToDb(
        firstName: String,
        lastName: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val userData = UserData(
            uid = auth.currentUser!!.uid,
            email = auth.currentUser!!.email!!,
            firstName = firstName,
            lastName = lastName
        )
        db.collection(N.USER).document(userData.uid).set(userData)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun editProfile(
        newFirstName: String,
        newLastName: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val map: MutableMap<String, Any> = HashMap()
        map["firstName"] = newFirstName
        map["lastName"] = newLastName
        db.collection(N.USER).document(auth.currentUser!!.uid).update(map)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}