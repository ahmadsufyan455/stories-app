package com.zerodev.dicostories.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zerodev.dicostories.R
import com.zerodev.dicostories.databinding.ActivityRegisterBinding
import com.zerodev.dicostories.model.RegisterModel
import com.zerodev.dicostories.view.auth.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        authViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[AuthViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            val name = binding.edName.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_fields), Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, getString(R.string.password_error), Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, getString(R.string.email_error), Toast.LENGTH_SHORT).show()
            } else {
                binding.progressBar.visibility = View.VISIBLE
                val registerData = RegisterModel(
                    name = name,
                    email = email,
                    password = password
                )
                authViewModel.register(registerData)
                authViewModel.isResponseSuccess().observe(this) { isResponseSuccess ->
                    if (isResponseSuccess) {
                        binding.progressBar.visibility = View.GONE
                        authViewModel.getRegisterResponse().observe(this) { response ->
                            if (response != null) {
                                startActivity(Intent(this, LoginActivity::class.java))
                                Toast.makeText(
                                    this,
                                    getString(R.string.account_created),
                                    Toast.LENGTH_SHORT
                                ).show()
                                finishAffinity()
                            }
                        }
                    } else {
                        binding.progressBar.visibility = View.GONE
                        authViewModel.getResponseMessage().observe(this) { message ->
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}