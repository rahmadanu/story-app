package com.dandev.storyapp.ui.home.list_story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dandev.storyapp.R
import com.dandev.storyapp.data.remote.model.story.Story
import com.dandev.storyapp.databinding.FragmentListStoryBinding
import com.dandev.storyapp.ui.home.list_story.adapter.FooterLoadStateAdapter
import com.dandev.storyapp.ui.home.list_story.adapter.ListStoryAdapter
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListStoryFragment : Fragment() {

    private var _binding: FragmentListStoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListStoryViewModel by viewModels()

    private val adapter: ListStoryAdapter by lazy {
        ListStoryAdapter { story, extras ->
            val action =
                ListStoryFragmentDirections.actionListStoryFragmentToDetailStoryFragment(story)
            findNavController().navigate(action, extras)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListStory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        setOnClickListener()
        initList()
        observeListStory()
        observeLogoutUser()
    }

    private fun setOnClickListener() {
        binding.apply {
            tvLogout.setOnClickListener {
                viewModel.logoutUser()
            }
            ivMaps.setOnClickListener {
                findNavController().navigate(R.id.action_listStoryFragment_to_mapsFragment)
            }
            fabAddStory.setOnClickListener {
                findNavController().navigate(R.id.action_listStoryFragment_to_addStoryFragment)
            }
        }
    }

    private fun observeListStory() {
        viewModel.storyResponse
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach(adapter::submitData)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
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
            adapter = this@ListStoryFragment.adapter.withLoadStateFooter(
                footer = FooterLoadStateAdapter { this@ListStoryFragment.adapter.retry() }
            )
        }
        adapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.rvListStory.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.rvListStory.visibility = View.GONE
                }
                is LoadState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.rvListStory.visibility = View.GONE
                    val errorState = loadState.source.refresh as LoadState.Error
                    Toast.makeText(requireContext(), errorState.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}