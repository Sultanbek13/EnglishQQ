package com.example.englishqq.presentation.ui.source.auth.signup

import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.SharedFlow

interface SignUpViewModel {

    val signUpFlow: SharedFlow<Resource<Any>>

    fun signUp(email: String, password: String, firstName: String, lastName: String)

    fun addUserToDb(firstName: String, lastName: String)

}