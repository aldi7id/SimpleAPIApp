package com.ajgroup.themoviedbapp.home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ajgroup.themoviedbapp.model.Result
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajgroup.themoviedbapp.R
import com.ajgroup.themoviedbapp.database.RegisterDatabase
import com.ajgroup.themoviedbapp.database.RegisterRepository
import com.ajgroup.themoviedbapp.databinding.HomeFragmentBinding
import com.ajgroup.themoviedbapp.model.GetMovieDiscovery
import com.ajgroup.themoviedbapp.profile.ProfileViewModel
import com.ajgroup.themoviedbapp.profile.ProfileViewModelFactory
import com.ajgroup.themoviedbapp.service.ApiClient
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    companion object{
        val MOVIE_ID = "MOVIE_ID"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)
        val factory = HomeViewModelFactory(repository, application)

        homeViewModel =
            ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        binding.homeLayout = homeViewModel

        binding.lifecycleOwner = this

        homeViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
                NavHostFragment.findNavController(this).navigate(action)
                homeViewModel.doneNavigating()
            }
        })

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
                       //binding.pbLoading.visibility = View.GONE
                       Toast.makeText(context, "Data Berhasil Di Load", Toast.LENGTH_SHORT).show()
                   } else{
                       //binding.pbLoading.visibility = View.GONE
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
                val actionToDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment()
                //actionToDetailFragment.actionId = id.toString().toInt()
                findNavController().navigate(actionToDetailFragment)
//                val intent = Intent(this@MainActivity, DetailMovieActivity::class.java)
//                intent.putExtra(DetailMovieActivity.ID, data.id)
//                startActivity(intent)
            }
        })
        adapter.submitData(data)
        binding.rvList.adapter = adapter
    }


//    private fun initRecyclerView() {
//        binding.rvList.layoutManager = LinearLayoutManager(this.context)
//        displayUsersList()
//    }


//    private fun displayUsersList(data: List<Result>) {
//        Log.i("MYTAG", "Inside ...UserDetails..Fragment")
//        binding.rvList.adapter = HomeAdapter()
////        homeViewModel.users.observe(viewLifecycleOwner, Observer {
////            binding.rvList.adapter = HomeAdapter(it)
////        })
//
//    }

}