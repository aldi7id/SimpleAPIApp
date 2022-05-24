package com.ajgroup.themoviedbapp.data

import com.ajgroup.themoviedbapp.data.room.FavoriteDao
import com.ajgroup.themoviedbapp.data.room.FavoriteEntity
import com.ajgroup.themoviedbapp.data.service.ApiService

class DetailRepository(private val apiService: ApiService,
                       private val favoriteDao: FavoriteDao
) {

    suspend fun getDetailMovie(movieId: Int) = apiService.getIdMovies(movieId)
    fun getFavoriteById(movieId: Int) = favoriteDao.readFavoriteById(movieId)
    fun addToFavorite(favorite: FavoriteEntity) = favoriteDao.insertFavorite(favorite)
    fun removeFromFavorite(favorite: FavoriteEntity) = favoriteDao.deleteFavorite(favorite)

}