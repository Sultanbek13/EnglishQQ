package com.example.englishqq.data.helper

import com.example.englishqq.data.N
import com.example.englishqq.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthHelper(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {

    fun signUp(email: String,
               password: String,
               firstName: String,
               lastName: String,
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

    fun addUserToDb(firstName: String, lastName: String, onSuccess: () -> Unit, onFailure: (msg: String?) -> Unit) {
        val user = User(uid = auth.currentUser!!.uid, email = auth.currentUser!!.email!!, firstName = firstName, lastName = lastName)
        db.collection(N.USER).document(user.uid).set(user)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getUserName(onSuccess: (user: User) -> Unit, onFailure: (msg: String?) -> Unit) {
        db.collection(N.USER).document(auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val res = it.toObject(User::class.java)!!
                onSuccess.invoke(res)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun editProfile(user: User, onSuccess: (user: User) -> Unit, onFailure: (msg: String?) -> Unit) {
        db.collection(N.USER).document(user.uid).set(user)
                .addOnSuccessListener {
                    onSuccess.invoke(user)
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }
}