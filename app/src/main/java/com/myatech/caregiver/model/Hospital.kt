package com.myatech.caregiver.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_hospital")
data class Hospital(
    @PrimaryKey
    var hid: Int?,

    @ColumnInfo(name = "hospital_name")
    var hospital_name: String,

    @ColumnInfo(name = "phone")
    var phone: String,

    @ColumnInfo(name = "address")
    var address: String,

    @ColumnInfo(name = "latitude")
    var latitude: Double,

    @ColumnInfo(name = "longitude")
    var longitude: Double,
)