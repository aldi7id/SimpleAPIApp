package com.ajgroup.themoviedbapp.ui.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ajgroup.themoviedbapp.data.room.FavoriteEntity
import com.ajgroup.themoviedbapp.databinding.FavoriteFragmentBinding
import com.ajgroup.themoviedbapp.data.FavoriteRepository
import com.ajgroup.themoviedbapp.data.room.RegisterDatabase

class FavoriteFragment : Fragment() {
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding



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
    private fun showFavoriteMovies(data: List<FavoriteEntity?>) {
        val adapter  = FavoriteAdapter(object : FavoriteAdapter.OnClickListener {
            override fun onClickItem(data: FavoriteEntity) {
                val id = data.id
                val actionToDetailFragment = FavoriteFragmentDirections.actionFavoriteFragmentToDetailMovieFragment(
                    movieId = id.toString().toInt()
                )
                findNavController().navigate(actionToDetailFragment)
            }
        })
        adapter.submitData(data)
        binding?.rvFavorite?.adapter = adapter
    }

}