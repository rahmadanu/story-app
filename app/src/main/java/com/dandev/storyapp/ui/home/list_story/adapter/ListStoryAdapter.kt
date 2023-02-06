package com.dandev.storyapp.ui.home.list_story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dandev.storyapp.data.remote.model.story.Story
import com.dandev.storyapp.databinding.ItemStoryBinding

class ListStoryAdapter(private val itemClick: (Story, FragmentNavigator.Extras) -> Unit) :
    RecyclerView.Adapter<ListStoryAdapter.ListStoryViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Story?>?) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListStoryViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ListStoryViewHolder, position: Int) {
        holder.bindView(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

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