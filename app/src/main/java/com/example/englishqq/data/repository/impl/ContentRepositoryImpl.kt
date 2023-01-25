package com.example.englishqq.data.repository.impl

import android.util.Log
import com.example.englishqq.data.model.CategoryData
import com.example.englishqq.data.model.ContentData
import com.example.englishqq.data.model.TestData
import com.example.englishqq.data.model.UserData
import com.example.englishqq.data.remote.AuthHelper
import com.example.englishqq.data.remote.ContentHelper
import com.example.englishqq.data.repository.ContentRepository

class ContentRepositoryImpl(private val contentHelper: ContentHelper) : ContentRepository {

    override suspend fun getListCategory(
        onSuccess: (categoryData: List<CategoryData?>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        contentHelper.getListCategory(
            {
                onSuccess.invoke(it)
            },
            {
                onFailure.invoke(it)
            }
        )
    }

    override suspend fun getListContent(
        typeId: String,
        onSuccess: (categoryData: List<ContentData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        contentHelper.getListContent(typeId,
            {
                onSuccess.invoke(it)
            },
            {
                onFailure.invoke(it)
            }
        )
    }

    override suspend fun getListTestContent(
        typeId: String,
        onSuccess: (testData: List<TestData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        contentHelper.getListTestContent(typeId,
            {
                onSuccess.invoke(it)
            },
            {
                onFailure.invoke(it)
            }
        )
    }

    override suspend fun saveFeedbackFromUser(
        typeId: String,
        userEmail: String,
        feedback: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        contentHelper.saveFeedbackFromUser(typeId, userEmail, feedback,
            {
                onSuccess.invoke()
            },
            {
                onFailure.invoke(it)
            }
        )
    }
}