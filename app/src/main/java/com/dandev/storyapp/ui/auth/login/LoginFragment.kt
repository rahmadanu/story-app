package com.dandev.storyapp.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dandev.storyapp.R
import com.dandev.storyapp.data.remote.model.auth.LoginRequest
import com.dandev.storyapp.databinding.FragmentLoginBinding
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        observeLoginResponse()
    }

    private fun observeLoginResponse() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
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
                    findNavController().navigate(R.id.action_loginFragment_to_listStoryFragment)
                }
                else -> {}
            }
        }
    }

    private fun setOnClickListener() {
        binding.apply {
            tvLabelRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {
                if (validateInput()) {
                    loginUser(parseFormIntoEntity())
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        binding.apply {
            val email = binding.edLoginEmail.text.toString().trim()
            val password = binding.edLoginPassword.text.toString().trim()

            if (email.isEmpty()) {
                isValid = false
                tilEmail.error = getString(R.string.error_empty_email)
                edLoginEmail.requestFocus()
            } else if (password.isEmpty()) {
                isValid = false
                tilPassword.error = getString(R.string.error_empty_password)
                edLoginPassword.requestFocus()
            }
        }
        return isValid
    }

    private fun loginUser(loginRequest: LoginRequest) {
        viewModel.loginUser(loginRequest)
    }

    private fun parseFormIntoEntity(): LoginRequest {
        return LoginRequest(
            email = binding.edLoginEmail.text.toString().trim(),
            password = binding.edLoginPassword.text.toString().trim(),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}