package com.ajgroup.themoviedbapp.service

import com.ajgroup.themoviedbapp.model.DetailMovieResponse
import com.ajgroup.themoviedbapp.model.GetMovieDiscovery
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("discover/movie?api_key=00dfa9ebae2c776e7509c85faa9a2e23")
    fun gettDiscovery(): Call<GetMovieDiscovery>

    @GET("movie/{movie_id}?api_key=00dfa9ebae2c776e7509c85faa9a2e23")
    fun getIdMovies(@Path("movie_id") movie_id: Int): Call<DetailMovieResponse>

}