package com.dandev.storyapp.ui.home.list_story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dandev.storyapp.data.remote.model.story.Story
import com.dandev.storyapp.databinding.ItemStoryBinding

class ListStoryAdapter(private val itemClick: (Story, FragmentNavigator.Extras) -> Unit) :
    PagingDataAdapter<Story, ListStoryAdapter.ListStoryViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListStoryViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ListStoryViewHolder, position: Int) {
        val story = getItem(position)
        story?.let { holder.bindView(it) }
    }

    class ListStoryViewHolder(
        private val binding: ItemStoryBinding,
        val itemClick: (Story, FragmentNavigator.Extras) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Story) {
            with(item) {
                with(binding) {
                    tvItemName.text = name
                    tvItemDescription.text = description

                    Glide.with(itemView)
                        .load(photoUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivItemPhoto)

                    ViewCompat.setTransitionName(ivItemPhoto, "photo$id")
                }

                itemView.setOnClickListener {
                    val extras = FragmentNavigatorExtras(
                        Pair(binding.ivItemPhoto, "detail_photo"),
                    )
                    itemClick(this, extras)
                }
            }

        }
    }

}