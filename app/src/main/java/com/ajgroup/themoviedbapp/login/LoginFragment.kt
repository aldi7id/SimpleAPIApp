package com.ajgroup.themoviedbapp.login

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
import androidx.navigation.fragment.NavHostFragment
import com.ajgroup.themoviedbapp.database.DataStoreManager
import com.ajgroup.themoviedbapp.database.RegisterDatabase
import com.ajgroup.themoviedbapp.database.RegisterRepository
import com.ajgroup.themoviedbapp.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    var name = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.name.observe(viewLifecycleOwner){
            name = it
        }
        loginViewModel.emailpreferences.observe(viewLifecycleOwner){
            if (it != ""){
                navigateUserDetails()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = LoginFragmentBinding.inflate(inflater, container,false)
        val application = requireNotNull(this.activity).application
        val dataStoreManager = DataStoreManager(requireContext())
        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao
        val repository = RegisterRepository(dao,dataStoreManager)
        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        binding.myLoginViewModel = loginViewModel
        binding.lifecycleOwner = this

        loginViewModel.navigatetoRegister.observe(viewLifecycleOwner){ hasFinished->
            if (hasFinished == true){
                displayUsersList()
                loginViewModel.doneNavigatingRegiter()
            }
        }
        loginViewModel.errortoast.observe(viewLifecycleOwner) { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoast()
            }
        }

        loginViewModel.errotoastUsername .observe(viewLifecycleOwner) { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "User doesnt exist,please Register!", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastErrorUsername()
            }
        }

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner) { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastInvalidPassword()
            }
        }

        loginViewModel.navigatetoUserDetails.observe(viewLifecycleOwner) { hasFinished->
            if (hasFinished == true){
                navigateUserDetails()
                loginViewModel.doneNavigatingUserDetails()
            }
        }

        return binding.root
    }
    private fun displayUsersList() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }
    private fun navigateUserDetails() {

        loginViewModel.setEmailPreferences(binding.etEmailTextfield.text.toString())
        loginViewModel.setNamaPreferences(name)
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        NavHostFragment.findNavController(this).navigate(action)
        Toast.makeText(context, "Login Sukses", Toast.LENGTH_SHORT).show()
    }
}