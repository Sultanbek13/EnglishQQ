package com.example.englishqq.data

import android.content.Context

class Settings(private val context: Context) {

    companion object {
        const val KEY_SIGNED_IN = "keySignedIn"
    }

    private val prefs = context.getSharedPreferences("${context.packageName}.prefs", Context.MODE_PRIVATE)
    var signedIn : Boolean
        set(value) = prefs.edit().putBoolean(KEY_SIGNED_IN,value).apply()
        get() = prefs.getBoolean(KEY_SIGNED_IN,false)

}