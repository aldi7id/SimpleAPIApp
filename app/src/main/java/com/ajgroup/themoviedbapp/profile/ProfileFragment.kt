package com.ajgroup.themoviedbapp.profile

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajgroup.themoviedbapp.R
import com.ajgroup.themoviedbapp.database.RegisterDatabase
import com.ajgroup.themoviedbapp.database.RegisterEntity
import com.ajgroup.themoviedbapp.database.RegisterRepository
import com.ajgroup.themoviedbapp.databinding.ProfileFragmentBinding
import com.ajgroup.themoviedbapp.home.HomeFragmentDirections
import com.ajgroup.themoviedbapp.home.HomeViewModel
import com.ajgroup.themoviedbapp.home.HomeViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment() : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private val sharedPrefFile = "kotlinsharedpreference"
    var sharedPreferences: SharedPreferences? = null
    companion object {
        //fun newInstance() = ProfileFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileFragmentBinding.inflate(inflater,container,false)
        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = ProfileRepository(dao)

        val factory = ProfileViewModelFactory(repository, application)

        profileViewModel =
            ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        binding.myViewModel = profileViewModel

        binding.lifecycleOwner = this

        profileViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
                NavHostFragment.findNavController(this).navigate(action)
                profileViewModel.doneNavigating()
            }
        })

        //initRecyclerView()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val userNameShared = sharedPreferences?.getString("user_key","")
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
        lifecycleScope.launch(Dispatchers.IO){
            profileViewModel.getUserName(userNameShared.toString())
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
                    val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                    editor.putString("user_key", binding.etUsername.editText?.text.toString())
                    editor.putString("name", binding.etFirstName.editText?.text.toString() + " " + binding.etLastName.editText?.text.toString() )
                    editor.apply()
                }
            }
        }
    }


//    private fun initRecyclerView() {
//        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this.context)
//        displayUsersList()
//    }


//    private fun displayUsersList() {
//        Log.i("MYTAG", "Inside ...UserDetails..Fragment")
//        profileViewModel.users.observe(viewLifecycleOwner, Observer {
//            binding.usersRecyclerView.adapter = ProfileAdapter(it)
//        })
//
//    }


}