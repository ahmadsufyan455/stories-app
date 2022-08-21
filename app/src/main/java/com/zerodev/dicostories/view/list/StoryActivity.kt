package com.zerodev.dicostories.view.list

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zerodev.dicostories.R
import com.zerodev.dicostories.databinding.ActivityStoryBinding
import com.zerodev.dicostories.utils.Constant.REQUEST_CODE_PERMISSIONS
import com.zerodev.dicostories.utils.Constant.REQUIRED_PERMISSIONS
import com.zerodev.dicostories.utils.UserPreferences
import com.zerodev.dicostories.view.add.AddStoryActivity
import com.zerodev.dicostories.view.auth.LoginActivity
import com.zerodev.dicostories.view.list.adapter.StoryAdapter
import com.zerodev.dicostories.view.list.viewmodel.StoryViewModel
import com.zerodev.dicostories.view.list.viewmodel.ViewModelFactory
import com.zerodev.dicostories.view.map.MapActivity

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var storyViewModel: StoryViewModel
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var userPref: UserPreferences

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.no_permission),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        checkPermission(it)
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        userPref = UserPreferences(this)
        val factory = ViewModelFactory.getInstance(this)
        storyAdapter = StoryAdapter()
        storyViewModel = ViewModelProvider(
            this,
            factory
        )[StoryViewModel::class.java]

        getData()
        showData()

        binding.refresh.setOnRefreshListener {
            getData()
            binding.refresh.isRefreshing = false
        }

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    private fun getData() {
        val token = userPref.getUser().token
        if (token != null) {
            storyViewModel.getStories(token).observe(this) { stories ->
                if (stories != null) {
                    storyAdapter.submitData(lifecycle, stories)
                }
            }
        }
    }

    private fun showData() {
        with(binding.rvStory) {
            layoutManager = LinearLayoutManager(this@StoryActivity)
            setHasFixedSize(true)
            adapter = storyAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.lang -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.logout -> {
                userPref.clearData()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
                true
            }
            R.id.maps -> {
                startActivity(Intent(this, MapActivity::class.java).apply {
                    putExtra("TOKEN", userPref.getUser().token)
                })
                true
            }
            else -> true
        }
    }
}