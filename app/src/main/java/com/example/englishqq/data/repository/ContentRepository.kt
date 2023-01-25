package com.example.englishqq.data.repository

import com.example.englishqq.data.model.CategoryData
import com.example.englishqq.data.model.ContentData
import com.example.englishqq.data.model.TestData
import com.example.englishqq.data.model.UserData

interface ContentRepository {

    suspend fun getListCategory(
        onSuccess: (categoryData: List<CategoryData?>) -> Unit,
        onFailure: (msg: String?) -> Unit
    )

    suspend fun getListContent(
        typeId: String,
        onSuccess: (categoryData: List<ContentData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    )

    suspend fun getListTestContent(
        typeId: String,
        onSuccess: (testData: List<TestData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    )

    suspend fun saveFeedbackFromUser(
        typeId: String,
        userEmail: String,
        feedback: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    )
}