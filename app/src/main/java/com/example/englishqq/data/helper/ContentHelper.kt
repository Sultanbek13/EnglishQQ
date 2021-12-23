package com.example.englishqq.data.helper

import com.example.englishqq.data.N
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.data.model.Material
import com.example.englishqq.data.model.Resource
import com.example.englishqq.data.model.TestType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ContentHelper(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {

    fun getThemeInfo(onSuccess: (material: List<Material>) -> Unit,
                     onFailure: (msg: String?) -> Unit) {
        db.collection(N.MATERIAL).get()
            .addOnSuccessListener {
                val res = it.documents.map { doc ->
                    doc.toObject(Material::class.java)!!
                }
                onSuccess.invoke(res)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getStudyItem(typeId: String?, onSuccess: (currentType: List<CurrentType>) -> Unit, onFailure: (msg: String?) -> Unit) {
        db.collection(N.MATERIAL).document(typeId!!).collection("currentType").get()
                .addOnSuccessListener {
                    val res = it.documents.map { doc->
                        doc.toObject(CurrentType::class.java)!!
                    }
                    onSuccess.invoke(res)
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }

    fun getTestItem(typeId: String?, onSuccess: (material: List<TestType>) -> Unit, onFailure: (msg: String?) -> Unit) {
        db.collection(N.MATERIAL).document(typeId!!).collection("currentTest").get()
                .addOnSuccessListener {
                    val res = it.documents.map { doc->
                        doc.toObject(TestType::class.java)!!
                    }
                    onSuccess.invoke(res)
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }
}