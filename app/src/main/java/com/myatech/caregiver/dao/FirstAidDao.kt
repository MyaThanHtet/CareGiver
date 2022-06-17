package com.myatech.caregiver.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myatech.caregiver.model.FirstAid

@Dao
interface FirstAidDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFirstAid(firstAid: FirstAid)

    @Query("SELECT * FROM tbl_first_aid ORDER BY name ASC")
    fun allFirstAid(): LiveData<MutableList<FirstAid>>
}