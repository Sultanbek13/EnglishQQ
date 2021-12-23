package com.example.englishqq.ui.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishqq.data.helper.ContentHelper
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.data.model.Material
import com.example.englishqq.data.model.Resource

class StudyViewModel(private val contentHelper: ContentHelper) : ViewModel() {

    private val mutableThemeInfo: MutableLiveData<Resource<List<CurrentType>>> = MutableLiveData()
    val themeInfo: LiveData<Resource<List<CurrentType>>> get() = mutableThemeInfo

    fun getStudyItem(typeId: String) {
        mutableThemeInfo.value = Resource.loading()
        contentHelper.getStudyItem(typeId,
                {
                    mutableThemeInfo.value = Resource.success(it)
                },
                {
                    mutableThemeInfo.value = Resource.error(it)
                }
        )
    }
}