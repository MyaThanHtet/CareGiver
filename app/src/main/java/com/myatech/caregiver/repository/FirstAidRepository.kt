package com.myatech.caregiver.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myatech.caregiver.dao.FirstAidDao
import com.myatech.caregiver.model.FirstAid


class FirstAidRepository(private val firstAidDao: FirstAidDao) {

    fun insert(firstAid: FirstAid) {
        firstAidDao.insertFirstAid(firstAid)
    }

    fun getAllFirstAid(): LiveData<MutableList<FirstAid>> {
      return  firstAidDao.allFirstAid()
    }

}