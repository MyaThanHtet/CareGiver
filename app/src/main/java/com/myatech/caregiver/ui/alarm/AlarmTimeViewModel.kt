package com.myatech.caregiver.ui.alarm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myatech.caregiver.model.Alarm
import com.myatech.caregiver.model.FirstAid
import com.myatech.caregiver.repository.AlarmTimeRepository
import com.myatech.caregiver.repository.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmTimeViewModel constructor(
    application: Application
) : ViewModel() {
    private val alarmTimeRepository: AlarmTimeRepository
    var alarmTimesList : LiveData<MutableList<Alarm>>
    init {
        val alarmDao = MyDatabase.getInstance(application).alarmDao()
        alarmTimeRepository = AlarmTimeRepository(alarmDao)
        alarmTimesList=alarmTimeRepository.getAllAlarm()
    }

    fun addAlarm(alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) {
        alarmTimeRepository.insert(alarm)
    }

    fun updateAlarm(id:Int,flag:Boolean)=viewModelScope.launch(Dispatchers.IO){
        alarmTimeRepository.updateAlarm(id,flag)
    }
}