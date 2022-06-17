package com.myatech.caregiver.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tbl_first_aid")
data class FirstAid(
    @PrimaryKey(autoGenerate = true)
    var faid: Int?,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "instruction")
    var instruction: String,

    @ColumnInfo(name = "caution")
    var caution: String,

    @ColumnInfo(name = "photo")
    var photo: String,
)