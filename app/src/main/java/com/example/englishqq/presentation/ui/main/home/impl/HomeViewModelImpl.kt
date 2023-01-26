package com.example.englishqq.presentation.ui.main.home.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishqq.data.model.CategoryData
import com.example.englishqq.data.repository.AuthRepository
import com.example.englishqq.data.repository.ContentRepository
import com.example.englishqq.presentation.ui.main.home.HomeViewModel
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class HomeViewModelImpl(
    private val authRepository: AuthRepository,
    private val contentRepository: ContentRepository,
) : HomeViewModel, ViewModel() {

    override val allCategoryFlow: MutableSharedFlow<Resource<List<CategoryData?>>> =
        MutableSharedFlow()

    override val getUserNameFlow: MutableSharedFlow<String?> = MutableSharedFlow()

    override fun getListCategory() {
        viewModelScope.launch {
            allCategoryFlow.emit(Resource.loading())
            contentRepository.getListCategory(
                {
                    viewModelScope.launch {
                        allCategoryFlow.emit(Resource.success(it))
                    }
                },
                {
                    viewModelScope.launch {
                        allCategoryFlow.emit(Resource.error(it))
                    }
                }
            )
        }
    }

    override fun getUserName() {
        viewModelScope.launch {
            getUserNameFlow.emit(authRepository.getUserName())
        }
    }
}