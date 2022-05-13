package com.ajgroup.themoviedbapp.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ajgroup.themoviedbapp.R
import com.ajgroup.themoviedbapp.database.*
import com.ajgroup.themoviedbapp.databinding.FavoriteFragmentBinding
import com.ajgroup.themoviedbapp.home.HomeViewModel
import com.ajgroup.themoviedbapp.home.HomeViewModelFactory
import com.ajgroup.themoviedbapp.profile.ProfileViewModel

class FavoriteFragment : Fragment() {
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding

//    private val favoriteViewModel by viewModels<FavoriteViewModel> {
//        FavoriteViewModelFactory(
//            FavoriteRepository(
//                RegisterDatabase.getInstance(requireContext())!!.favoriteDao
//            )
//        )
//    }


    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoriteFragmentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).favoriteDao
        val repository = FavoriteRepository(dao)
        val factory = FavoriteViewModelFactory(repository, application)

        favoriteViewModel =
            ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)

        favoriteViewModel.allFavorites.observe(viewLifecycleOwner){
            //show adapter
            showFavoriteMovies(it)
        }

        favoriteViewModel.getAllFavorites()
    }
    private fun showFavoriteMovies(list: List<Favorite?>?) {
        val adapter= FavoriteAdapter {
            val action = FavoriteFragmentDirections
                .actionFavoriteFragmentToDetailMovieFragment(it.id!!)
            findNavController().navigate(action)
        }
        adapter.submitList(list)
        binding?.rvFavorite?.adapter = adapter
    }

}