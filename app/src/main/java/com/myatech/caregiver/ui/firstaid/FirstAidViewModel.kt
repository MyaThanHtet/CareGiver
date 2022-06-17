package com.myatech.caregiver.ui.firstaid

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myatech.caregiver.model.FirstAid
import com.myatech.caregiver.repository.FirstAidRepository
import com.myatech.caregiver.repository.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstAidViewModel constructor(
    application: Application
) : ViewModel() {
    private val firstAidRepository: FirstAidRepository
    var firstAidList : LiveData<MutableList<FirstAid>>

    init {
        val firstDao = MyDatabase.getInstance(application).firstAidDao()
        firstAidRepository = FirstAidRepository(firstDao)
        firstAidList=firstAidRepository.getAllFirstAid()
    }

    fun addFirstAid(firstAid: FirstAid) = viewModelScope.launch(Dispatchers.IO) {
        firstAidRepository.insert(firstAid)
    }

}