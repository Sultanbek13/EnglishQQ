package com.example.englishqq.presentation.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishqq.data.remote.AuthHelper
import com.example.englishqq.utils.Resource
import com.example.englishqq.data.model.UserData

class SettingViewModel(private val auth: AuthHelper) : ViewModel() {

    private val mutableProfile: MutableLiveData<Resource<String>> = MutableLiveData()
    val profileInfo: LiveData<Resource<String>> get() = mutableProfile

    fun editProfile(userData: UserData) {
        mutableProfile.value = Resource.loading()
        auth.editProfile(userData,
                {
                    mutableProfile.value = Resource.success("success")
                },
                {
                    mutableProfile.value = Resource.error(it)
                }
        )
    }
}