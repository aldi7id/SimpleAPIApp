package com.ajgroup.themoviedbapp.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.ajgroup.themoviedbapp.R
import com.ajgroup.themoviedbapp.databinding.DetailMovieFragmentBinding
import com.ajgroup.themoviedbapp.databinding.HomeFragmentBinding
import com.ajgroup.themoviedbapp.home.HomeViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.detail_movie_fragment.*

class DetailMovieFragment : Fragment() {
    private lateinit var detailMovieViewModel: DetailMovieViewModel
    private var _binding: DetailMovieFragmentBinding? = null
    private val binding get() = _binding!!
    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"
    val args: DetailMovieFragmentArgs by navArgs()
    companion object {
        fun newInstance() = DetailMovieFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailMovieFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailMovieViewModel = ViewModelProvider(this).get(DetailMovieViewModel::class.java)
        val movieId = args.movieId

        detailMovieViewModel.detailMovie.observe(viewLifecycleOwner){
            binding.tvJudul.text = it.originalTitle
            binding.tvDesc.text = it.overview
            Glide.with(binding.root).load(IMAGE_BASE+it.backdropPath).into(iv_header)
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
        detailMovieViewModel.getDetailsMovie(movieId)
    }

}