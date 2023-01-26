package com.example.englishqq.data.repository

interface AuthRepository {

    suspend fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    )

    suspend fun signUp(
        email: String, password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    )

    suspend fun addUserToDb(
        firstName: String,
        lastName: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    )

    suspend fun editProfile(
        newFirstName: String,
        newLastName: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    )

    suspend fun setStateAuth(state: Boolean)

    suspend fun getStateAuth(): Boolean

    suspend fun saveUserName(userName: String)

    suspend fun getUserName(): String

    suspend fun saveUserEmail(userEmail: String)

    suspend fun getUserEmail(): String

    suspend fun saveLastName(lastName: String)

    suspend fun getLastName(): String

}