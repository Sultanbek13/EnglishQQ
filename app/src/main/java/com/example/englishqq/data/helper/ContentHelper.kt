package com.example.englishqq.data.helper

import android.util.Log
import com.example.englishqq.data.N
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.data.model.Material
import com.google.firebase.firestore.FirebaseFirestore

class ContentHelper(private val db: FirebaseFirestore) {

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

    fun getListWords(onSuccess: (currentType: List<CurrentType>) -> Unit, onFailure: (msg: String?) -> Unit) {
        db.collection(N.MATERIAL).document("greeting").collection("currentType").get()
                .addOnSuccessListener {
                    val res = it.documents.map { doc->
                        doc.toObject(CurrentType::class.java)!!
                    }
                    onSuccess.invoke(res)
                    Log.d("asd", "SUCCESSFULLY")
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }
}