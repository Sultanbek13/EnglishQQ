package com.example.englishqq.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishqq.data.helper.AuthHelper
import com.example.englishqq.data.model.Material
import com.example.englishqq.data.model.Resource
import com.example.englishqq.data.model.User

class SettingViewModel(private val auth: AuthHelper) : ViewModel() {

    private val mutableUser: MutableLiveData<Resource<User>> = MutableLiveData()
    val userInfo: LiveData<Resource<User>> get() = mutableUser

    fun getUser() {
        mutableUser.value = Resource.loading()
        auth.getUserName(
                {
                    mutableUser.value = Resource.success(it)
                },
                {
                    mutableUser.value = Resource.error(it)
                }
        )
    }

    private val mutableProfile: MutableLiveData<Resource<String>> = MutableLiveData()
    val profileInfo: LiveData<Resource<String>> get() = mutableProfile

    fun editProfile(user: User) {
        mutableProfile.value = Resource.loading()
        auth.editProfile(user,
                {
                    mutableProfile.value = Resource.success("success")
                },
                {
                    mutableProfile.value = Resource.error(it)
                }
        )
    }
}