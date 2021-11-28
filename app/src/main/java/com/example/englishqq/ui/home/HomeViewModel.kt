package com.example.englishqq.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishqq.data.helper.ContentHelper
import com.example.englishqq.data.model.Material
import com.example.englishqq.data.model.Resource

class HomeViewModel(private val contentHelper: ContentHelper) : ViewModel(){

    private val mutableThemeInfo: MutableLiveData<Resource<List<Material>>> = MutableLiveData()
    val themeInfo: LiveData<Resource<List<Material>>> get() = mutableThemeInfo

    fun getThemeInfo() {
        mutableThemeInfo.value = Resource.loading()
        contentHelper.getThemeInfo(
            {
                mutableThemeInfo.value = Resource.success(it)
            },
            {
                mutableThemeInfo.value = Resource.error(it)
            }
        )
    }
}