package com.zerodev.dicostories.view.list.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zerodev.dicostories.databinding.ItemStoryBinding
import com.zerodev.dicostories.model.Story
import com.zerodev.dicostories.view.detail.DetailStoryActivity

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    private val stories = ArrayList<Story>()

    @SuppressLint("NotifyDataSetChanged")
    fun setStories(listStory: List<Story>) {
        stories.clear()
        stories.addAll(listStory)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.tvUser.text = story.name
            Picasso.get().load(story.photoUrl).into(binding.imgStory)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailStoryActivity::class.java).apply {
                    putExtra(DetailStoryActivity.EXTRA_DETAIL, story)
                }
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.imgStory, "image"),
                        Pair(binding.tvUser, "name"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stories[position])
    }

    override fun getItemCount(): Int = stories.count()
}