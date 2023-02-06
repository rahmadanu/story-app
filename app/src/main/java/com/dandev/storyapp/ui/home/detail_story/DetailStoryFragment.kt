package com.dandev.storyapp.ui.home.detail_story

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dandev.storyapp.R
import com.dandev.storyapp.data.remote.model.story.Story
import com.dandev.storyapp.databinding.FragmentDetailStoryBinding
import com.dandev.storyapp.util.date.getMilliseconds
import com.dandev.storyapp.util.date.toTimeAgo

class DetailStoryFragment : Fragment() {

    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!

    private val navArgs: DetailStoryFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

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

        postponeEnterTransition()
        setOnClickListener()
        bindDetailStoryToView(navArgs.detailStory)
    }

    private fun setOnClickListener() {
        binding.ivUpButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bindDetailStoryToView(detailStory: Story) {
        binding.apply {
            detailStory.apply {
                tvDetailName.text = name
                tvDetailUploaded.text = createdAt?.let { getMilliseconds(it).toTimeAgo() }
                tvDetailDescription.text = description

                Glide.with(requireContext())
                    .load(photoUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            startPostponedEnterTransition()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            startPostponedEnterTransition()
                            return false
                        }
                    })
                    .into(ivDetailPhoto)

                ViewCompat.setTransitionName(ivDetailPhoto, "detail_photo")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}