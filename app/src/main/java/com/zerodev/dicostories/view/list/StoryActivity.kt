package com.zerodev.dicostories.view.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zerodev.dicostories.R
import com.zerodev.dicostories.databinding.ActivityStoryBinding
import com.zerodev.dicostories.utils.ThemePreferences
import com.zerodev.dicostories.utils.UserPreferences
import com.zerodev.dicostories.view.add.AddStoryActivity
import com.zerodev.dicostories.view.auth.LoginActivity
import com.zerodev.dicostories.view.list.adapter.StoryAdapter
import com.zerodev.dicostories.view.list.viewmodel.StoryViewModel
import com.zerodev.dicostories.view.list.viewmodel.ViewModelFactory

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var storyViewModel: StoryViewModel
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var userPref: UserPreferences
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private var isDarkModeActive: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPref = UserPreferences(this)
        val themePref = ThemePreferences.getInstance(dataStore)
        storyAdapter = StoryAdapter()
        storyViewModel = ViewModelProvider(
            this,
            ViewModelFactory(themePref)
        )[StoryViewModel::class.java]

        getData()
        showData()
        getThemeSetting()

        binding.refresh.setOnRefreshListener {
            getData()
            binding.refresh.isRefreshing = false
        }

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    private fun getData() {
        storyViewModel.setStories(userPref.getUser().token!!)
        storyViewModel.getStories().observe(this) { stories ->
            if (stories != null) {
                storyAdapter.setStories(stories)
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

    private fun getThemeSetting() {
        storyViewModel.getThemeSettings().observe(this) { isDarkMode ->
            isDarkModeActive = if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                false
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
            R.id.mode -> {
                storyViewModel.saveThemeSetting(isDarkModeActive)
                true
            }
            else -> true
        }
    }
}