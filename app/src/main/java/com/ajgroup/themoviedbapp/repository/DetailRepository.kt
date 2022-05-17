package com.ajgroup.themoviedbapp.repository

import com.ajgroup.themoviedbapp.dao.FavoriteDao
import com.ajgroup.themoviedbapp.database.entity.FavoriteEntity
import com.ajgroup.themoviedbapp.service.ApiService

class DetailRepository(//private val apiService: ApiService,
                       private val favoriteDao: FavoriteDao) {

    //suspend fun getDetailMovie(movieId: Int) = apiService.getIdMovies(movieId)
    fun getFavoriteById(movieId: Int) = favoriteDao.readFavoriteById(movieId)
    fun addToFavorite(favorite: FavoriteEntity) = favoriteDao.insertFavorite(favorite)
    fun removeFromFavorite(favorite: FavoriteEntity) = favoriteDao.deleteFavorite(favorite)

}