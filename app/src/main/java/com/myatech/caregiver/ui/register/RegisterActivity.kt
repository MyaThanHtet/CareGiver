package com.myatech.caregiver.ui.register

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.myatech.caregiver.databinding.ActivityRegisterBinding
import com.myatech.caregiver.model.User
import com.myatech.caregiver.ui.firstaid.FirstAidActivity
import com.myatech.caregiver.ui.login.LoginActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    private val defaultButtonTintColor = "#848484"
    private val onFormValidButtonTintColor = "#D90077"
    private var errorMessage: String? = null
    private val _username = MutableStateFlow("")
    private val _phone = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _confirm = MutableStateFlow("")

    private val formIsValid = combine(
        _username,
        _phone,
        _password,
        _confirm
    ) { _username, _phone, _password, _confirm ->


        val usernameIsValid = _username.isNotEmpty()
        val phoneIsValid = _phone.isNotEmpty()
        val passwordIsValid = _password.isNotEmpty()
        val confirmPasswordIsValid = _confirm.isNotEmpty()

        errorMessage = when {
            usernameIsValid.not() -> "fill name"
            phoneIsValid.not() -> "fill phone"
            passwordIsValid.not() -> "fill password"
            confirmPasswordIsValid.not() -> "retype password"
            else -> null
        }

        errorMessage?.let {

        }

        usernameIsValid and phoneIsValid and passwordIsValid and confirmPasswordIsValid
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)


        registerViewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory(application)
        )[RegisterViewModel::class.java]


        //form is empty validation
        with(registerBinding) {
            registerUsernameEdt.doOnTextChanged { text, _, _, _ ->
                _username.value = text.toString()
            }
            registerGmailPhoneEdt.doOnTextChanged { text, _, _, _ ->
                _phone.value = text.toString()
            }
            registerPasswordEdt.doOnTextChanged { text, _, _, _ ->
                _password.value = text.toString()
            }
            registerConfirmPasswordEdt.doOnTextChanged { text, _, _, _ ->
                _confirm.value = text.toString()
            }

        }
        lifecycleScope.launch {
            formIsValid.collect {
                registerBinding.registerBtn.apply {
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



        registerBinding.registerBtn.setOnClickListener {
            when (_password.value) {
                _confirm.value -> {

                    val user = User(
                        userid = null,
                        username = _username.value,
                        contact = _phone.value,
                        password = _password.value
                    )
                    register(user)

                }
                else -> {
                    Toast.makeText(applicationContext, "Password doesn't match", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            registerBinding.registerLoginTv.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun register(user: User) {
        registerViewModel.registerUser(user)
        val intent = Intent(this, FirstAidActivity::class.java)
        intent.putExtra("userType", "admin")
        startActivity(intent)
        finish()
    }
}