package com.ajgroup.themoviedbapp.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.ajgroup.themoviedbapp.database.RegisterDatabase
import com.ajgroup.themoviedbapp.database.RegisterRepository
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
//

        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = RegisterViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        binding.myViewModel = registerViewModel

        binding.lifecycleOwner = this

        registerViewModel.navigateto.observe(viewLifecycleOwner) { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                displayUsersList()
                registerViewModel.doneNavigating()
            }
        }

        registerViewModel.userDetailsLiveData.observe(viewLifecycleOwner) {
            Log.i("MYTAG",it.toString()+"000000000000000000000000")
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
        Log.i("MYTAG","insidisplayUsersList")
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

}