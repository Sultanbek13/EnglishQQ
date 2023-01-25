package com.example.englishqq.data.model

data class UserData(
    val uid: String = "",
    val email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    val createdDate: Long = System.currentTimeMillis()
)