package com.example.englishqq.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishqq.data.helper.AuthHelper
import com.example.englishqq.data.model.Resource

class SignInViewModel(private val authHelper: AuthHelper) : ViewModel() {

    private var mutableSignInStatus: MutableLiveData<Resource<Any?>> = MutableLiveData()

    val signInStatus : LiveData<Resource<Any?>>
        get() = mutableSignInStatus

    fun signIn(email: String, password: String) {
        mutableSignInStatus.value = Resource.loading()
        authHelper.signIn(email, password,
            {
                mutableSignInStatus.value = Resource.success(null)
            },
            {
                mutableSignInStatus.value = Resource.error(it)
            }
        )
    }
}