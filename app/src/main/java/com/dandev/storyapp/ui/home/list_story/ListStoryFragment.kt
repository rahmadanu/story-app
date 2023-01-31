package com.dandev.storyapp.ui.home.list_story

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dandev.storyapp.R
import com.dandev.storyapp.databinding.FragmentListStoryBinding
import com.dandev.storyapp.ui.home.list_story.adapter.ListStoryAdapter

class ListStoryFragment : Fragment() {

    private var _binding: FragmentListStoryBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListStoryAdapter by lazy {
        ListStoryAdapter{
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
    }

    private fun initList() {
        binding.rvListStory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ListStoryFragment.adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}