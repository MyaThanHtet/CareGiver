package com.myatech.caregiver.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myatech.caregiver.model.Alarm
import com.myatech.caregiver.model.FirstAid

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAlarm(alarm: Alarm)
    @Query("SELECT * FROM tbl_alarm")
    fun allAlarm(): LiveData<MutableList<Alarm>>

    @Query("UPDATE tbl_alarm SET flag=:flag WHERE alid=:id")
    fun updateAlarm(id:Int,flag:Boolean)
}