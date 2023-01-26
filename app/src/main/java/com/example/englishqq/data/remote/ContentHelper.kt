package com.example.englishqq.data.remote

import com.example.englishqq.data.model.CategoryData
import com.example.englishqq.data.model.ContentData
import com.example.englishqq.data.model.TestData
import com.example.englishqq.utils.N
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ContentHelper(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {

    fun getListCategory(
        onSuccess: (categoryData: List<CategoryData?>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection(N.MATERIAL).get()
            .addOnSuccessListener {
                val res = it.documents.map { doc ->
                    doc.toObject(CategoryData::class.java)
                }
                onSuccess.invoke(res)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getListContent(
        typeId: String?,
        onSuccess: (contentData: List<ContentData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection(N.MATERIAL).document(typeId!!).collection("currentType").get()
            .addOnSuccessListener {
                val res = it.documents.map { doc ->
                    doc.toObject(ContentData::class.java)!!
                }
                onSuccess.invoke(res)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getListTestContent(
        typeId: String?,
        onSuccess: (testData: List<TestData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection(N.MATERIAL).document(typeId!!).collection("currentTest").get()
            .addOnSuccessListener {
                val res = it.documents.map { doc ->
                    doc.toObject(TestData::class.java)!!
                }
                onSuccess.invoke(res)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun saveFeedbackFromUser(
        typeId: String,
        userEmail: String,
        feedback: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val map: MutableMap<String, Any> = HashMap()
        map["feedback"] = feedback
        db.collection(N.MATERIAL).document(typeId).collection(N.FEEDBACK).document(userEmail)
            .set(map)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.message)
            }
    }
}