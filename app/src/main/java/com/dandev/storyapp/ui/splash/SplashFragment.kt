package com.dandev.storyapp.ui.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dandev.storyapp.R
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeIsUserLoggedIn()
    }

    private fun observeIsUserLoggedIn() {
        viewModel.isLoggedIn().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Empty -> {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_splashFragment_to_listStoryFragment)
                }
                else -> {}
            }
        }
    }
}