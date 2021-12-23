package com.example.englishqq.data.model

data class UserInfo(
        val id : String = "",
        val stepIf : ArrayList<Int> = arrayListOf(),
        val stepPastSimple : ArrayList<Int> = arrayListOf()
)