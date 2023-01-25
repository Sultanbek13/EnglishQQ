package com.example.englishqq.data.pref

interface SharedPref {

    fun setStateAuth(state: Boolean)

    fun getStateAuth(): Boolean

    fun saveUserName(userName: String)

    fun getUserName(): String

    fun saveUserEmail(userEmail: String)

    fun getUserEmail(): String

}