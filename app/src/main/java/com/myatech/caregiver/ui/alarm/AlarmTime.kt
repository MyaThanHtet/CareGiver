package com.myatech.caregiver.ui.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.myatech.caregiver.adapter.AlarmTimeAdapter
import com.myatech.caregiver.adapter.ItemClickListener
import com.myatech.caregiver.databinding.ActivityAlarmTimeLayoutBinding
import com.myatech.caregiver.model.Alarm
import com.myatech.caregiver.utils.AlarmReceiver
import java.util.*

class AlarmTime : AppCompatActivity(), ItemClickListener {
    private lateinit var alarmTimeLayoutBinding: ActivityAlarmTimeLayoutBinding
    private lateinit var alarmTimeViewModel: AlarmTimeViewModel

    private lateinit var alarmTimeAdapter: AlarmTimeAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmTimeLayoutBinding = ActivityAlarmTimeLayoutBinding.inflate(layoutInflater)
        setContentView(alarmTimeLayoutBinding.root)

        alarmTimeAdapter = AlarmTimeAdapter(this)
        alarmTimeLayoutBinding.alarmTimeRecyclerView.adapter = alarmTimeAdapter

        alarmTimeViewModel = ViewModelProvider(
            this,
            AlarmTimeViewModelFactory(application)
        )[AlarmTimeViewModel::class.java]
        val calendar: Calendar = Calendar.getInstance()

        alarmTimeLayoutBinding.timePicker.setOnTimeChangedListener { timePicker, _, _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    timePicker.hour,
                    timePicker.minute,
                    0
                )
            }
        }
        alarmTimeLayoutBinding.alarmAddButton.setOnClickListener {
            val alarm = Alarm(null, calendar.timeInMillis.toString(), false)
            alarmTimeViewModel.addAlarm(alarm)
        }

        alarmTimeViewModel.alarmTimesList.observe(this) {
            alarmTimeAdapter.setAlarmList(it)
            Log.d("ALARMTIMECHECK", "$it")

        }
    }


    override fun setAlarmTimer(flag: Boolean, alarm: Alarm) {
        alarm.alid?.let { alarmTimeViewModel.updateAlarm(it, flag) }
        Toast.makeText(applicationContext, "$flag :: ${alarm.alid}", Toast.LENGTH_SHORT).show()
        if(flag){
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
            } else {
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                alarm.alarm_time.toLong(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }else{
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
            } else {
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            alarmManager.cancel(pendingIntent)
        }
    }
}
