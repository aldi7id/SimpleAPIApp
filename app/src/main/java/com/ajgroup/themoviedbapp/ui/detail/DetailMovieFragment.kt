package com.ajgroup.themoviedbapp.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ajgroup.themoviedbapp.R
import com.ajgroup.themoviedbapp.data.room.RegisterDatabase
import com.ajgroup.themoviedbapp.data.room.FavoriteEntity
import com.ajgroup.themoviedbapp.databinding.DetailMovieFragmentBinding
import com.ajgroup.themoviedbapp.data.DetailRepository
import com.ajgroup.themoviedbapp.data.service.ApiClient
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.detail_movie_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailMovieFragment : Fragment() {
    private lateinit var detailMovieViewModel: DetailMovieViewModel
    private var _binding: DetailMovieFragmentBinding? = null
    private val binding get() = _binding!!
    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"
    val args: DetailMovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailMovieFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dao = RegisterDatabase.getInstance(application).favoriteDao
        val repository = DetailRepository(apiService = ApiClient.getInstance(requireContext()) , dao)
        val factory = DetailMovieVideModelFactory(repository,application)
        detailMovieViewModel =
            ViewModelProvider(this, factory).get(DetailMovieViewModel::class.java)
        val movieId = args.movieId

        detailMovieViewModel.detailMovie.observe(viewLifecycleOwner){
            binding.apply {
                tvJudul.text = getString(R.string.tittless).plus(it.originalTitle)
                tvGenre.text = getString(R.string.genre).plus(it.genres[0].name ).plus(getString(R.string.or)).plus(it.genres[1].name)
                tvRelease.text = getString(R.string.release).plus(it.releaseDate)
                tvTagLine.text = getString(R.string.tagline).plus(it.tagline)
                tvStatus.text = getString(R.string.status).plus(it.status)
                tvDesc.text = getString(R.string.Desc).plus(it.overview)
            }
            Glide.with(binding.root).load(IMAGE_BASE+it.backdropPath).into(iv_header)
            Toast.makeText(context, getString(R.string.success_load), Toast.LENGTH_SHORT).show()
        }
        detailMovieViewModel.getDetailsMovie(movieId)
        binding.ivFavorite.setOnClickListener {_ ->
            lifecycleScope.launch(Dispatchers.IO){
                val isFavorite = detailMovieViewModel.getFavoriteById(movieId)
                activity?.runOnUiThread {
                    if (isFavorite == null){
                        val newFavorite = FavoriteEntity(
                            id = id,
                        overview = id.toString(),
                        posterPath = id.toString(),
                        voteAverage = id.toDouble())
                        lifecycleScope.launch(Dispatchers.IO){
                            detailMovieViewModel.addToFavorite(newFavorite)
                            runBlocking(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Movie ditambahkan ke favorit!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        detailMovieViewModel.changeFavorite(true)
                    }else{
                        lifecycleScope.launch(Dispatchers.IO){
                            detailMovieViewModel.removeFromFavorite(isFavorite)
                            runBlocking(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Movie dihapus ke favorit!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        detailMovieViewModel.changeFavorite(false)
                    }
                }
            }
        }
    }



}