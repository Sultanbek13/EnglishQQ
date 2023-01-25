package com.example.englishqq.data.remote

import com.example.englishqq.utils.N
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserAnalysis(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {

    fun getUserInfo(onSuccess: (material: List<UserAnalysis?>) -> Unit, onFailure: (msg: String?) -> Unit) {
        db.collection(N.USER).document(auth.currentUser!!.uid).collection("currentInfo").get()
                .addOnSuccessListener {
                    val res = it.documents.map { doc->
                        doc.toObject(UserAnalysis::class.java)
                    }
                    onSuccess.invoke(res)
                }
                .addOnFailureListener {
                    it.localizedMessage
                }
    }

/*    fun setFeedback(userDataInfo: UserInfoData, onSuccess: (userAnalysis: UserInfoData) -> Unit, onFailure: (msg: String?) -> Unit) {
        db.collection(N.USER).document(auth.currentUser!!.uid).collection("currentInfo").document(userDataInfo.feedback).set(feedback)
                .addOnSuccessListener {
                    onSuccess.invoke(userDataInfo)
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }*/
}