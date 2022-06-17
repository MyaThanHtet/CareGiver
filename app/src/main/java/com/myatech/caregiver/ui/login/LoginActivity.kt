package com.myatech.caregiver.ui.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.myatech.caregiver.databinding.ActivityLoginBinding
import com.myatech.caregiver.ui.firstaid.FirstAidActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    private val defaultButtonTintColor = "#848484"
    private val onFormValidButtonTintColor = "#D90077"
    private var errorMessage: String? = null
    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    private val formIsValid = combine(
        _username,
        _password,
    ) { _username, _password ->


        val usernameIsValid = _username.isNotEmpty()
        val passwordIsValid = _password.isNotEmpty()

        errorMessage = when {
            usernameIsValid.not() -> "fill name"
            passwordIsValid.not() -> "fill password"

            else -> null
        }

        errorMessage?.let {

        }

        usernameIsValid and passwordIsValid
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(application)
        )[LoginViewModel::class.java]


        //form is empty validation
        with(loginBinding) {
            loginUsernameEdt.doOnTextChanged { text, _, _, _ ->
                _username.value = text.toString()
            }


            loginPasswordEdt.doOnTextChanged { text, _, _, _ ->
                _password.value = text.toString()
            }

        }

        loginBinding.goToNewsfeedTv.setOnClickListener {
            val intent = Intent(this, FirstAidActivity::class.java)
            intent.putExtra("userType", "user")
            startActivity(intent)
            finish()
        }

        lifecycleScope.launch {
            formIsValid.collect {
                loginBinding.loginBtn.apply {
                    backgroundTintList = ColorStateList.valueOf(
                        Color.parseColor(
                            if (it) {

                                onFormValidButtonTintColor
                            } else {
                                defaultButtonTintColor
                            }
                        )

                    )

                    isClickable = it


                }
            }
        }

        loginBinding.loginBtn.setOnClickListener {
            loginUser(
                loginBinding.loginUsernameEdt.text.toString(),
                loginBinding.loginPasswordEdt.text.toString()
            )
        }


    }

    private fun loginUser(username: String, password: String) {
        loginViewModel.checkUser(username, password).observe(this) {
            it?.username?.let { it1 ->
                Log.d(
                    "CHECKSIGNEDINUSER", it1
                )
                if (it1.isNotEmpty()) {
                    val intent = Intent(this, FirstAidActivity::class.java)
                    intent.putExtra("userType", "admin")
                    startActivity(intent)
                    finish()
                }
            }
        }

    }
}