package com.ajgroup.themoviedbapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajgroup.themoviedbapp.model.DetailMovieResponse
import com.ajgroup.themoviedbapp.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieViewModel : ViewModel() {
    private val _detailMovieViewModel: MutableLiveData<DetailMovieResponse> = MutableLiveData()
    val detailMovie: LiveData<DetailMovieResponse> = _detailMovieViewModel

    fun getDetailsMovie(movieId: Int){
        ApiClient.instace.getIdMovies(movieId)
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


}