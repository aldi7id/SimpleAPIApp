package com.ajgroup.themoviedbapp.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.ajgroup.themoviedbapp.data.room.DataStoreManager
import com.ajgroup.themoviedbapp.data.room.RegisterDatabase
import com.ajgroup.themoviedbapp.data.RegisterRepository
import com.ajgroup.themoviedbapp.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)

        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao
        val dataStoreManager = DataStoreManager(requireContext())
        val repository = RegisterRepository(dao,dataStoreManager)

        val factory = RegisterViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        binding.myViewModel = registerViewModel

        binding.lifecycleOwner = this

        registerViewModel.navigateto.observe(viewLifecycleOwner) { hasFinished->
            if (hasFinished == true){
                displayUsersList()
                registerViewModel.doneNavigating()
            }
        }



        registerViewModel.errotoast.observe(viewLifecycleOwner) { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoast()
            }
        }
        registerViewModel.successtoast.observe(viewLifecycleOwner) { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoast()
            }
        }

        registerViewModel.errotoastUsername.observe(viewLifecycleOwner) { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "UserName Already taken", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoastUserName()
            }
        }
        registerViewModel.errotoastPassword.observe(viewLifecycleOwner) { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Password Tidak Sama", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoast()
            }
        }

        return binding.root
    }

    private fun displayUsersList() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

}