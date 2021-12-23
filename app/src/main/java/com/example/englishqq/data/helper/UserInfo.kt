package com.example.englishqq.data.helper

import com.example.englishqq.data.N
import com.example.englishqq.data.model.TestType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserInfo(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {

    fun getUserInfo(onSuccess: (material: List<UserInfo?>) -> Unit, onFailure: (msg: String?) -> Unit) {
        db.collection(N.USER).document(auth.currentUser!!.uid).collection("currentInfo").get()
                .addOnSuccessListener {
                    val res = it.documents.map { doc->
                        doc.toObject(UserInfo::class.java)
                    }
                    onSuccess.invoke(res)
                }
                .addOnFailureListener {
                    it.localizedMessage
                }
    }
}