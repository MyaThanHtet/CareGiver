package com.myatech.caregiver.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myatech.caregiver.dao.AlarmDao
import com.myatech.caregiver.dao.FirstAidDao
import com.myatech.caregiver.dao.HospitalDao
import com.myatech.caregiver.dao.UserDao
import com.myatech.caregiver.model.Alarm
import com.myatech.caregiver.model.FirstAid
import com.myatech.caregiver.model.Hospital
import com.myatech.caregiver.model.User

@Database(
    entities = [Alarm::class, FirstAid::class, Hospital::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
    abstract fun firstAidDao(): FirstAidDao
    abstract fun hospitalDao(): HospitalDao
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: MyDatabase? = null
        fun getInstance(context: Context): MyDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MyDatabase::class.java,
                    "db_caregiver"
                )
                    .build()
            }

            return INSTANCE as MyDatabase
        }
    }
}