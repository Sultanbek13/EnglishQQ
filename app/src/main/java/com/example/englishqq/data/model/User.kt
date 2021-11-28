package com.example.englishqq.data.model

data class User(
    val uid: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val createdDate: Long = System.currentTimeMillis()
)