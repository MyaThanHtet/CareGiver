package com.myatech.caregiver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myatech.caregiver.databinding.ActivityMainBinding
import com.myatech.caregiver.ui.login.LoginActivity
import com.myatech.caregiver.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}