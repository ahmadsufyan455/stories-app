package com.zerodev.dicostories.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.zerodev.dicostories.R
import com.zerodev.dicostories.databinding.ActivityDetailStoryBinding
import com.zerodev.dicostories.model.Story

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var story: Story

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.detail)

        story = intent.getParcelableExtra<Story>(EXTRA_DETAIL) as Story

        populateView()
    }

    private fun populateView() {
        Picasso.get().load(story.photoUrl).into(binding.imgPhoto)
        binding.tvName.text = story.name
        binding.tvDesc.text = story.description
    }

    companion object {
        const val EXTRA_DETAIL = "detail_story"
    }
}