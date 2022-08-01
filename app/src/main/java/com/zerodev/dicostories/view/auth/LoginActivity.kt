package com.zerodev.dicostories.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zerodev.dicostories.R
import com.zerodev.dicostories.databinding.ActivityLoginBinding
import com.zerodev.dicostories.model.LoginModel
import com.zerodev.dicostories.utils.UserPreferences
import com.zerodev.dicostories.view.auth.viewmodel.AuthViewModel
import com.zerodev.dicostories.view.list.StoryActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userPref: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.login)

        authViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[AuthViewModel::class.java]

        userPref = UserPreferences(this)

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_fields), Toast.LENGTH_SHORT).show()
            } else {
                binding.progressBar.visibility = View.VISIBLE
                val loginData = LoginModel(email = email, password = password)
                authViewModel.login(loginData)
            }
        }

        authViewModel.isResponseSuccess().observe(this) { isResponseSuccess ->
            if (isResponseSuccess) {
                binding.progressBar.visibility = View.GONE
                authViewModel.getLoginResponse().observe(this) { response ->
                    if (response != null) {
                        userPref.setUser(response.loginResult)
                        moveToHome()
                    }
                }
            } else {
                binding.progressBar.visibility = View.GONE
                authViewModel.getResponseMessage().observe(this) { message ->
                    Toast.makeText(this, message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        if (userPref.getUser().token!!.isNotEmpty()) {
            moveToHome()
        }
    }

    private fun moveToHome() {
        startActivity(Intent(this, StoryActivity::class.java))
        finish()
    }
}