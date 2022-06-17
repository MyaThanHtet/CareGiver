package com.myatech.caregiver.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_alarm")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var alid: Int?,

    @ColumnInfo(name = "alarm_time")
    var alarm_time:String,

    @ColumnInfo(name = "flag")
    var flag: Boolean,
)