package com.dandev.storyapp.ui.home.list_story

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dandev.storyapp.R
import com.dandev.storyapp.databinding.FragmentListStoryBinding
import com.dandev.storyapp.ui.home.list_story.adapter.ListStoryAdapter
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListStoryFragment : Fragment() {

    private var _binding: FragmentListStoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListStoryViewModel by viewModels()

    private val adapter: ListStoryAdapter by lazy {
        ListStoryAdapter{
            val action = ListStoryFragmentDirections.actionListStoryFragmentToDetailStoryFragment(it)
            findNavController().navigate(action)
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

        setOnClickListener()
        initList()
        getAllStories()
        observeListStory()
        observeLogoutUser()
    }

    private fun setOnClickListener() {
        binding.apply {
            tvLogout.setOnClickListener {
                viewModel.logoutUser()
            }
            fabAddStory.setOnClickListener {
                findNavController().navigate(R.id.action_listStoryFragment_to_addStoryFragment)
            }
        }
    }

    private fun observeListStory() {
        viewModel.listStoryResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbLoading.isVisible = true
                }
                is Resource.Error -> {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.pbLoading.isVisible = false
                    adapter.submitList(it.data?.listStory)
                }
                else -> {}
            }
        }
    }

    private fun getAllStories() {
        viewModel.getAllStories()
    }

    private fun observeLogoutUser() {
        viewModel.logoutResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_listStoryFragment_to_loginFragment)
                }
                else -> {}
            }
        }
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