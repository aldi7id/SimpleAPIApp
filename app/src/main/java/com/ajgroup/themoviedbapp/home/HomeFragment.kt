package com.ajgroup.themoviedbapp.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ajgroup.themoviedbapp.model.Result
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ajgroup.themoviedbapp.R
import com.ajgroup.themoviedbapp.database.DataStoreManager
import com.ajgroup.themoviedbapp.database.RegisterDatabase
import com.ajgroup.themoviedbapp.repository.RegisterRepository
import com.ajgroup.themoviedbapp.databinding.HomeFragmentBinding
import com.ajgroup.themoviedbapp.model.GetMovieDiscovery
import com.ajgroup.themoviedbapp.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao
        val dataStoreManager = DataStoreManager(requireContext())
        val repository = RegisterRepository(dao,dataStoreManager)
        val factory = HomeViewModelFactory(repository, application)

        homeViewModel =
            ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        binding.homeLayout = homeViewModel

        binding.lifecycleOwner = this

        homeViewModel.navigateto.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished == true) {
                val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
                NavHostFragment.findNavController(this).navigate(action)
                homeViewModel.doneNavigating()
            }
        }
        homeViewModel.navigatetofavorite.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished == true) {
                val action = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
                NavHostFragment.findNavController(this).navigate(action)
                homeViewModel.doneNavigatingfavorite()
            }
        }
        homeViewModel.homeViewModel.observe(viewLifecycleOwner){
            binding.welcome.text = getString(R.string.welcome).plus(it?.firstName).plus(" ").plus(it?.lastName)
        }
        homeViewModel.emailpref.observe(viewLifecycleOwner){
            lifecycleScope.launch(Dispatchers.IO){
                homeViewModel.getUserName(it)
            }
        }


        fetchAllData()
    }



    private fun fetchAllData() {
       ApiClient.instace.gettDiscovery()
           .enqueue(object : retrofit2.Callback<GetMovieDiscovery>
           {
               override fun onResponse(
                   call: Call<GetMovieDiscovery>,
                   response: Response<GetMovieDiscovery>
               ) {
                   val body = response.body()
                   val code = response.code()
                   if (code == 200){
                       body?.results?.let { showList(it) }
                       Toast.makeText(context, "Data Berhasil Di Load", Toast.LENGTH_SHORT).show()
                   } else{
                       Toast.makeText(context, "Data Gagal Di Load", Toast.LENGTH_SHORT).show()
                   }
               }

               override fun onFailure(call: Call<GetMovieDiscovery>, t: Throwable) {
               }

           })
    }

    private fun showList(data: List<Result>) {
        val adapter  = HomeAdapter(object : HomeAdapter.OnClickListener {
            override fun onClickItem(data: Result) {
                val id = data.id
                val actionToDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(
                    movieId = id.toString().toInt()
                )
                findNavController().navigate(actionToDetailFragment)
            }
        })
        adapter.submitData(data)
        binding.rvList.adapter = adapter
    }



}