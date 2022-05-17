package com.ajgroup.themoviedbapp.detail

import android.app.Application
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajgroup.themoviedbapp.database.entity.FavoriteEntity
import com.ajgroup.themoviedbapp.model.DetailMovieResponse
import com.ajgroup.themoviedbapp.repository.DetailRepository
import com.ajgroup.themoviedbapp.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieViewModel(private val repository: DetailRepository, private val application: Application) : ViewModel() {
    private val _detailMovieViewModel: MutableLiveData<DetailMovieResponse> = MutableLiveData()
    val detailMovie: LiveData<DetailMovieResponse> = _detailMovieViewModel

    fun getDetailsMovie(movieId: Int){
        ApiClient.getInstance(application).getIdMovies(movieId)
            .enqueue(object : Callback<DetailMovieResponse> {
                override fun onResponse(
                    call: Call<DetailMovieResponse>,
                    response: Response<DetailMovieResponse>
                ) {
                    if (response.isSuccessful){
                        _detailMovieViewModel.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                }

            })
    }
    private val _isFavoriteExist = MutableLiveData<Boolean>()
    val isFavoriteExist = _isFavoriteExist

    fun changeFavorite(state: Boolean){
        _isFavoriteExist.postValue(state)
    }

    fun getFavoriteById(movieId: Int) = repository.getFavoriteById(movieId)
    fun addToFavorite(favorite: FavoriteEntity) = repository.addToFavorite(favorite)
    fun removeFromFavorite(favorite: FavoriteEntity) = repository.removeFromFavorite(favorite)

}

