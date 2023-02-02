package com.dandev.storyapp.ui.auth.register

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
import com.dandev.storyapp.data.remote.model.auth.RegisterRequest
import com.dandev.storyapp.databinding.FragmentRegisterBinding
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        observeRegisterResponse()
    }

    private fun observeRegisterResponse() {
        viewModel.registerResponse.observe(viewLifecycleOwner) {
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
                    findNavController().navigateUp()
                    Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun setOnClickListener() {
        binding.apply {
            tvLabelLogIn.setOnClickListener { findNavController().navigateUp() }
            btnRegister.setOnClickListener {
                if (validateInput()) {
                    registerUser(parseFormIntoEntity())
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        binding.apply {
            val name = edRegisterName.text.toString().trim()
            val email = edRegisterEmail.text.toString().trim()
            val password = edRegisterPassword.text.toString().trim()

            if (name.isEmpty()) {
                isValid = false
                tilName.error = getString(R.string.error_empty_name)
                edRegisterName.requestFocus()
            } else if (email.isEmpty()) {
                isValid = false
                tilEmail.error = getString(R.string.error_empty_email)
                edRegisterEmail.requestFocus()
            } else if (password.isEmpty()) {
                isValid = false
                tilPassword.error = getString(R.string.error_empty_password)
                edRegisterPassword.requestFocus()
            }
        }
        return isValid
    }

    private fun registerUser(registerRequest: RegisterRequest) {
        viewModel.registerUser(registerRequest)
    }

    private fun parseFormIntoEntity(): RegisterRequest {
        return RegisterRequest(
            name = binding.edRegisterName.text.toString().trim(),
            email = binding.edRegisterEmail.text.toString().trim(),
            password = binding.edRegisterPassword.text.toString().trim(),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}