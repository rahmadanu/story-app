package com.dandev.storyapp.ui.home.detail_story

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dandev.storyapp.R
import com.dandev.storyapp.data.remote.model.story.Story
import com.dandev.storyapp.databinding.FragmentDetailStoryBinding

class DetailStoryFragment : Fragment() {

    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!

    private val navArgs: DetailStoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindDetailStoryToView(navArgs.detailStory)
    }

    private fun bindDetailStoryToView(detailStory: Story) {
        binding.apply {
            detailStory.apply {
                tvDetailName.text = name
                tvDetailUploaded.text = createdAt
                tvDetailDescription.text = description

                Glide.with(requireContext())
                    .load(photoUrl)
                    .into(ivDetailPhoto)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}