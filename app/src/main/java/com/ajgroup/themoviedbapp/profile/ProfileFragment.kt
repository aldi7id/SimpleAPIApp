package com.ajgroup.themoviedbapp.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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

class ProfileFragment() : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
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

        val repository = RegisterRepository(dao)

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

        initRecyclerView()

        return binding.root

    }


    private fun initRecyclerView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this.context)
        displayUsersList()
    }


    private fun displayUsersList() {
        Log.i("MYTAG", "Inside ...UserDetails..Fragment")
        profileViewModel.users.observe(viewLifecycleOwner, Observer {
            binding.usersRecyclerView.adapter = ProfileAdapter(it)
        })

    }


}