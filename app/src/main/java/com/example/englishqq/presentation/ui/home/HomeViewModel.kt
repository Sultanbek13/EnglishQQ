package com.example.englishqq.presentation.ui.home

import com.example.englishqq.data.model.CategoryData
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.SharedFlow

interface HomeViewModel {

    val allCategoryFlow: SharedFlow<Resource<List<CategoryData?>>>

    val getUserNameFlow: SharedFlow<String?>

    fun getListCategory()

    fun getUserName()

}