package com.example.englishqq.data.repository.impl

import com.example.englishqq.data.pref.SharedPref
import com.example.englishqq.data.remote.AuthHelper
import com.example.englishqq.data.repository.AuthRepository
import com.example.englishqq.utils.Resource

class AuthRepositoryImpl(
    private val sharedPref: SharedPref,
    private val authHelper: AuthHelper
) : AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        authHelper.signIn(email, password,
            {
                onSuccess.invoke()
            },
            {
                onFailure.invoke(it)
            })
    }

    override suspend fun signUp(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        authHelper.signUp(
            email,
            password,
            {
                onSuccess.invoke()
            },
            {
                onFailure.invoke(it)
            }
        )
    }

    override suspend fun addUserToDb(
        firstName: String,
        lastName: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        authHelper.addUserToDb(firstName, lastName,
            {
                onSuccess.invoke()
            },
            {
                onFailure.invoke(it)
            })
    }

    override suspend fun setStateAuth(state: Boolean) = sharedPref.setStateAuth(state)

    override suspend fun getStateAuth(): Boolean = sharedPref.getStateAuth()

    override suspend fun saveUserName(userName: String) = sharedPref.saveUserName(userName)

    override suspend fun getUserName(): String = sharedPref.getUserName()

    override suspend fun saveUserEmail(userEmail: String) = sharedPref.saveUserEmail(userEmail)

    override suspend fun getUserEmail(): String = sharedPref.getUserEmail()
}