package com.example.englishqq.presentation.ui.test

import com.example.englishqq.data.model.TestData
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.SharedFlow

interface TestViewModel {

    val allTestFlow: SharedFlow<Resource<List<TestData>>>

    fun getListTestContent(typeId: String)

}