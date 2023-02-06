package com.dandev.storyapp.ui.home.add_story

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dandev.storyapp.R
import com.dandev.storyapp.databinding.FragmentAddStoryBinding
import com.dandev.storyapp.util.image.createCustomTempFile
import com.dandev.storyapp.util.image.reduceFileImage
import com.dandev.storyapp.util.image.uriToFile
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class AddStoryFragment : Fragment() {

    private var _binding: FragmentAddStoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddStoryViewModel by viewModels()

    private var getFile: File? = null
    private lateinit var currentPhotoPath: String

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = BitmapFactory.decodeFile(getFile?.path)
            binding.ivAddImagePlaceholder.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())

            getFile = myFile
            binding.ivAddImagePlaceholder.setImageURI(selectedImg)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        observeAddNewStory()
    }

    private fun setOnClickListener() {
        binding.apply {
            btnAddCamera.setOnClickListener { startCamera() }
            btnAddGallery.setOnClickListener { startGallery() }
            btnAddStory.setOnClickListener {
                if (validateInput()) {
                    val description = binding.edAddDescription.text.toString().trim()
                    getFile?.let { photoFile -> addNewStory(photoFile, description) }   
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        binding.apply {
            val description = edAddDescription.text.toString().trim()

            if (getFile == null) {
                isValid = false
                Toast.makeText(requireContext(), getString(R.string.message_upload_photo_required), Toast.LENGTH_SHORT).show()
            } else if (description.isEmpty()) {
                isValid = false
                edAddDescription.error = getString(R.string.error_empty_description)
                edAddDescription.requestFocus()
            }
        }
        return isValid
    }

    private fun addNewStory(photo: File, description: String) {
        val reducedPhoto = reduceFileImage(photo)
        viewModel.addNewStory(reducedPhoto, description)
    }

    private fun observeAddNewStory() {
        viewModel.addNewStoryResponse.observe(viewLifecycleOwner) {
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
                    Toast.makeText(requireContext(), getString(R.string.message_add_story_success), Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addStoryFragment_to_listStoryFragment)
                }
                else -> {}
            }
        }
    }

    private fun startGallery() {
        val intent = Intent().apply {
            action = ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, getString(R.string.title_choose_a_picture))
        launcherIntentGallery.launch(chooser)
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireActivity().application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                getString(R.string.package_authority),
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}