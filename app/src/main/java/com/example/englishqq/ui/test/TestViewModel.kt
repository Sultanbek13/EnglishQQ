package com.example.englishqq.ui.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishqq.data.helper.ContentHelper
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.data.model.Resource
import com.example.englishqq.data.model.TestType

class TestViewModel(private val contentHelper: ContentHelper) : ViewModel() {
    private val mutableTestInfo: MutableLiveData<Resource<List<TestType>>> = MutableLiveData()
    val testInfo: LiveData<Resource<List<TestType>>> get() = mutableTestInfo

    fun getTestItem(typeId: String) {
        mutableTestInfo.value = Resource.loading()
        contentHelper.getTestItem(typeId,
                {
                    mutableTestInfo.value = Resource.success(it)
                },
                {
                    mutableTestInfo.value = Resource.error(it)
                }
        )
    }
}