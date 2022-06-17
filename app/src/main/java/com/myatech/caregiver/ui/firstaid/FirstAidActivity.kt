package com.myatech.caregiver.ui.firstaid


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.myatech.caregiver.R
import com.myatech.caregiver.adapter.FirstAidAdapter
import com.myatech.caregiver.databinding.ActivityFirstAidLayoutBinding
import com.myatech.caregiver.model.FirstAid
import com.myatech.caregiver.nearbyplaces.MapsActivity
import com.myatech.caregiver.ui.alarm.AlarmTime
import com.myatech.caregiver.ui.register.RegisterActivity
import kotlinx.coroutines.launch

class FirstAidActivity : AppCompatActivity() {
    private lateinit var firstAidLayoutBinding: ActivityFirstAidLayoutBinding
    private lateinit var firstAidViewModel: FirstAidViewModel
    private lateinit var firstAidAdapter: FirstAidAdapter

    private var isAllFabVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstAidLayoutBinding = ActivityFirstAidLayoutBinding.inflate(layoutInflater)
        setContentView(firstAidLayoutBinding.root)
        fabConfig()
        val userType: String = intent?.getStringExtra("userType").toString()

        when {
            userType != "admin" -> {
                firstAidLayoutBinding.registrationFab.isEnabled = false
                firstAidLayoutBinding.addNewsfeedFab.isEnabled = false
            }
        }

        firstAidViewModel = ViewModelProvider(
            this,
            FirstAidViewModelFactory(application)
        )[FirstAidViewModel::class.java]
        firstAidAdapter = FirstAidAdapter()
        firstAidLayoutBinding.firstAidRecyclerView.adapter = firstAidAdapter



        firstAidViewModel.firstAidList.observe(this) {
            firstAidAdapter.setFirstAidsList(it)
        }

        firstAidLayoutBinding.addNewsfeedFab.setOnClickListener {
            addNewsfeedDialog()
        }
        firstAidLayoutBinding.setAlarmFab.setOnClickListener {
            startActivity(Intent(this, AlarmTime::class.java))
        }
        firstAidLayoutBinding.searchHospitalFab.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
        firstAidLayoutBinding.registrationFab.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        firstAidLayoutBinding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                firstAidAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun fabConfig() {
        firstAidLayoutBinding.registrationFab.visibility = View.GONE
        firstAidLayoutBinding.registrationFabText.visibility = View.GONE
        firstAidLayoutBinding.addNewsfeedFab.visibility = View.GONE
        firstAidLayoutBinding.addNewsfeedFabText.visibility = View.GONE
        firstAidLayoutBinding.setAlarmFab.visibility = View.GONE
        firstAidLayoutBinding.setAlarmFabText.visibility = View.GONE
        firstAidLayoutBinding.searchHospitalFab.visibility = View.GONE
        firstAidLayoutBinding.searchHospitalFabText.visibility = View.GONE

        firstAidLayoutBinding.addFab.shrink()

        lifecycleScope.launch {

            firstAidLayoutBinding.addFab.setOnClickListener {

                if (!isAllFabVisible) {
                    firstAidLayoutBinding.registrationFab.show()
                    firstAidLayoutBinding.registrationFabText.visibility = View.VISIBLE
                    firstAidLayoutBinding.addNewsfeedFab.show()
                    firstAidLayoutBinding.addNewsfeedFabText.visibility = View.VISIBLE
                    firstAidLayoutBinding.setAlarmFab.show()
                    firstAidLayoutBinding.setAlarmFabText.visibility = View.VISIBLE
                    firstAidLayoutBinding.searchHospitalFab.show()
                    firstAidLayoutBinding.searchHospitalFabText.visibility = View.VISIBLE
                    firstAidLayoutBinding.addFab.extend()
                    isAllFabVisible = true
                } else {
                    firstAidLayoutBinding.registrationFab.hide()
                    firstAidLayoutBinding.registrationFabText.visibility = View.GONE
                    firstAidLayoutBinding.addNewsfeedFab.hide()
                    firstAidLayoutBinding.addNewsfeedFabText.visibility = View.GONE
                    firstAidLayoutBinding.setAlarmFab.hide()
                    firstAidLayoutBinding.setAlarmFabText.visibility = View.GONE
                    firstAidLayoutBinding.searchHospitalFab.hide()
                    firstAidLayoutBinding.searchHospitalFabText.visibility = View.GONE
                    firstAidLayoutBinding.addFab.shrink()
                    isAllFabVisible = false
                }
            }
        }


    }

    private fun addNewsfeedDialog() {
        val dialog = Dialog(this)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_newsfeed_dialog_layout)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val nameEtd = dialog.findViewById<EditText>(R.id.editText_add_newsfeed_name)
        val instructionEdt = dialog.findViewById<EditText>(R.id.editText_add_newsfeed_instruction)
        val cautionEdt = dialog.findViewById<EditText>(R.id.editText_add_newsfeed_caution)
        val photoPathEdt = dialog.findViewById<EditText>(R.id.editText_add_newsfeed_photo_path)
        val saveBtn = dialog.findViewById<Button>(R.id.add_newsfeed_save_btn)


        saveBtn.setOnClickListener {
            val firstAid = FirstAid(
                null, nameEtd.text.toString(),
                instructionEdt.text.toString(),
                cautionEdt.text.toString(),
                photoPathEdt.text.toString()
            )
            if (nameEtd.text.isNotEmpty() && instructionEdt.text.isNotEmpty() && cautionEdt.text.isNotEmpty() && photoPathEdt.text.isNotEmpty()) {
                firstAidViewModel.addFirstAid(firstAid)

                dialog.dismiss()
                firstAidLayoutBinding.registrationFab.hide()
                firstAidLayoutBinding.registrationFabText.visibility = View.GONE
                firstAidLayoutBinding.addNewsfeedFab.hide()
                firstAidLayoutBinding.addNewsfeedFabText.visibility = View.GONE
                firstAidLayoutBinding.setAlarmFab.hide()
                firstAidLayoutBinding.setAlarmFabText.visibility = View.GONE
                firstAidLayoutBinding.searchHospitalFab.hide()
                firstAidLayoutBinding.searchHospitalFabText.visibility = View.GONE
                firstAidLayoutBinding.addFab.shrink()
            }


        }
        dialog.show()
    }
}