package com.myatech.caregiver.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myatech.caregiver.databinding.AlarmTimeItemLayoutBinding
import com.myatech.caregiver.model.Alarm
import java.text.SimpleDateFormat
import java.util.*

class AlarmTimeAdapter(private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<AlarmTimeAdapterViewHolder>() {

    var alarmTimes = mutableListOf<Alarm>()

    @SuppressLint("NotifyDataSetChanged")
    fun setAlarmList(alarmTimes: MutableList<Alarm>) {
        this.alarmTimes = alarmTimes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmTimeAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlarmTimeItemLayoutBinding.inflate(inflater, parent, false)
        return AlarmTimeAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmTimeAdapterViewHolder, position: Int) {
        val alarmTime = alarmTimes[position]
        val simpleDateFormat = SimpleDateFormat("hh:mm a")
        val timeString = simpleDateFormat.format(alarmTime.alarm_time.toLong())
        holder.binding.alarmTimeTv.text = timeString
        holder.binding.alarmOnOffSwitch.isChecked = alarmTime.flag
        holder.binding.alarmOnOffSwitch.setOnCheckedChangeListener { _, _ ->

            itemClickListener.setAlarmTimer(holder.binding.alarmOnOffSwitch.isChecked, alarmTime)
        }
    }

    override fun getItemCount(): Int {
        return alarmTimes.size
    }

}

class AlarmTimeAdapterViewHolder(val binding: AlarmTimeItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
}

interface ItemClickListener {
    fun setAlarmTimer(flag: Boolean, alarm: Alarm)
}