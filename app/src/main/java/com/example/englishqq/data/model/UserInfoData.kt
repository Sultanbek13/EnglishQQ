package com.example.englishqq.data.model

data class UserInfoData(
        val id : String = "",
        val stepIf : ArrayList<Int> = arrayListOf(),
        val stepPastSimple : MutableList<Int> = mutableListOf(),
        val feedback : MutableList<String> = mutableListOf()
)