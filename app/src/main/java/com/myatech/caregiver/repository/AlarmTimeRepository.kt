package com.myatech.caregiver.repository

import androidx.lifecycle.LiveData
import com.myatech.caregiver.dao.AlarmDao
import com.myatech.caregiver.model.Alarm

class AlarmTimeRepository(private val alarmDao: AlarmDao) {

    fun insert(alarm: Alarm) {
        alarmDao.insertAlarm(alarm)
    }

    fun getAllAlarm(): LiveData<MutableList<Alarm>> {
        return alarmDao.allAlarm()
    }

    fun updateAlarm(id: Int, flag: Boolean) {
        alarmDao.updateAlarm(id, flag)
    }
}