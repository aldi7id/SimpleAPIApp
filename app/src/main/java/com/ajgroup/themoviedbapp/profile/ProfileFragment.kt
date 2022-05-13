package com.ajgroup.themoviedbapp.profile



import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.ajgroup.themoviedbapp.R
import com.ajgroup.themoviedbapp.database.DataStoreManager
import com.ajgroup.themoviedbapp.database.RegisterDatabase
import com.ajgroup.themoviedbapp.database.RegisterEntity
import com.ajgroup.themoviedbapp.database.RegisterRepository
import com.ajgroup.themoviedbapp.databinding.ProfileFragmentBinding
import com.ajgroup.themoviedbapp.home.HomeFragmentDirections
import com.ajgroup.themoviedbapp.utils.PermissionUtils
import com.bumptech.glide.Glide

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment() : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null
    private var selectedImage = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater,container,false)
        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao
        val dataStoreManager = DataStoreManager(requireContext())
        val repository = RegisterRepository(dao,dataStoreManager)

        val factory = ProfileViewModelFactory(repository, application)

        profileViewModel =
            ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        binding.myViewModel = profileViewModel

        binding.lifecycleOwner = this

        profileViewModel.navigateto.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished == true) {
                val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
                NavHostFragment.findNavController(this).navigate(action)
                profileViewModel.doneNavigating()
            }
        }
    binding.btnChangeProfile.setOnClickListener {
        if (PermissionUtils.isPermissionsGranted(requireActivity(), getRequiredPermission())){
            openGallery()
        }
    }
        return binding.root

    }
    private var galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data?.data
            imageUri?.let { loadImage(it) }
            selectedImage = true
        }
    }
    private fun loadImage(uri: Uri) {
        binding.ivLogo.setImageURI(uri)
    }
    private fun openGallery() {
        val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        galleryLauncher.launch(intentGallery)
    }

    private fun getRequiredPermission(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userNameShared: String
        profileViewModel.emailpreferences.observe(viewLifecycleOwner){
            userNameShared = it
            profileViewModel.getUserName(userNameShared)
        }

        var userId:Int? = -1
        profileViewModel.profileViewModel.observe(viewLifecycleOwner){
            userId = it?.userId
            binding.apply {
                etFirstName.editText?.setText(it?.firstName)
                etLastName.editText?.setText(it?.lastName)
                etUsername.editText?.setText(it?.userName)
                etPassword.editText?.setText(it?.passwrd)
            }
        }

        binding.btnSubmit.setOnClickListener {
            val firstName = binding.etFirstName.editText?.text.toString()
            val lastName = binding.etLastName.editText?.text.toString()
            val userName = binding.etUsername.editText?.text.toString()
            val password = binding.etPassword.editText?.text.toString()

            val updateUser = RegisterEntity(
                userId.toString().toInt(),
                firstName,
                lastName,
                userName,
                password
            )
            lifecycleScope.launch(Dispatchers.IO){
                val update = profileViewModel.submitUpdate(updateUser)
                profileViewModel.getUserName(userName)
                activity?.runOnUiThread{
                    if (update!=0){
                        Toast.makeText(context, "Save Data Berhasil", Toast.LENGTH_SHORT).show()
                    }
                    profileViewModel.setEmailPreferences(binding.etUsername.editText?.text.toString())
                }
            }
        }
        binding.btnLogout.setOnClickListener {
            profileViewModel.deletePref()
            it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show()
        }

    }
}