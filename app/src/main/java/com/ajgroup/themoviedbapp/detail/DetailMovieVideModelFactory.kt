package com.ajgroup.themoviedbapp.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajgroup.themoviedbapp.home.HomeViewModel
import com.ajgroup.themoviedbapp.repository.DetailRepository
import java.lang.IllegalArgumentException

class DetailMovieVideModelFactory(private val repository: DetailRepository, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailMovieViewModel::class.java)) {
            return DetailMovieViewModel(repository,application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}