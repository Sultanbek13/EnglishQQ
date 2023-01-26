package com.example.englishqq.data.pref.impl

import android.content.Context
import com.example.englishqq.data.pref.SharedPref

class SharedPrefImpl(context: Context) : SharedPref {

    private val prefs =
        context.getSharedPreferences("${context.packageName}.prefs", Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    override fun setStateAuth(state: Boolean) = editor.putBoolean(STATE_AUTH, state).apply()

    override fun getStateAuth(): Boolean = prefs.getBoolean(STATE_AUTH, false)

    override fun saveUserName(userName: String) = editor.putString(USER_NAME, userName).apply()

    override fun getUserName(): String = prefs.getString(USER_NAME, "") ?: ""

    override fun saveUserEmail(userEmail: String) = editor.putString(USER_EMAIL, userEmail).apply()

    override fun getUserEmail(): String = prefs.getString(USER_EMAIL, "") ?: ""

    override fun saveLastName(lastName: String) = editor.putString(USER_LAST_NAME, lastName).apply()

    override fun getLastName(): String = prefs.getString(USER_LAST_NAME, "") ?: ""

    companion object {
        const val STATE_AUTH = "state_auth"
        const val USER_NAME = "user_name"
        const val USER_EMAIL = "user_email"
        const val USER_LAST_NAME = "user_last_name"
    }
}