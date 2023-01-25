package com.example.englishqq.presentation.ui.source.auth.signin

import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.SharedFlow

interface SignInViewModel {

    val signInFlow: SharedFlow<Resource<Any?>>

    fun signIn(email: String, password: String)

}