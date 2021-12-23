package com.example.englishqq.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishqq.data.helper.AuthHelper
import com.example.englishqq.data.helper.ContentHelper
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.data.model.Material
import com.example.englishqq.data.model.Resource
import com.example.englishqq.data.model.User

class HomeViewModel(private val contentHelper: ContentHelper, private val authHelper: AuthHelper) : ViewModel(){

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

    private val mutableUserInfo: MutableLiveData<Resource<User>> = MutableLiveData()
    val userInfo: LiveData<Resource<User>> get() = mutableUserInfo

    fun getUserInfo() {
        mutableUserInfo.value = Resource.loading()
        authHelper.getUserName(
            {
                mutableUserInfo.value = Resource.success(it)
            },
            {
                mutableUserInfo.value = Resource.error(it)
            }
        )
    }
}