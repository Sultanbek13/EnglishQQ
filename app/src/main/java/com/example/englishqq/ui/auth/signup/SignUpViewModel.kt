package com.example.englishqq.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishqq.data.helper.AuthHelper
import com.example.englishqq.data.model.Resource

class SignUpViewModel(private val authHelper: AuthHelper) : ViewModel() {

    private var mutableSignUpStatus : MutableLiveData<Resource<String?>> = MutableLiveData()
    val signUpStatus : LiveData<Resource<String?>>
        get() = mutableSignUpStatus

    fun signUp(email: String, password: String, firstName: String, lastName: String) {
        mutableSignUpStatus.value = Resource.loading()
        authHelper.signUp(email,password,firstName,lastName,
            {
                addUserToDb(firstName, lastName)
            },
            {
                mutableSignUpStatus.value = Resource.error(it)
            })
    }

    private fun addUserToDb(firstName: String, lastName: String) {
        authHelper.addUserToDb(firstName, lastName,
            {
                mutableSignUpStatus.value = Resource.success(null)
            },
            {
                mutableSignUpStatus.value = Resource.error(it)
            })
    }
}