package com.myatech.caregiver.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tbl_user")
data class User(
    @PrimaryKey
    var userid: Int?,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "contact")
    var contact: String,

    @ColumnInfo(name = "password")
    var password: String,
)
