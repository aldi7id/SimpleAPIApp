package com.ajgroup.themoviedbapp.database

class FavoriteRepository (private val favoriteDao: FavoriteDao){
    suspend fun getAllFavorites() = favoriteDao.readFavorites()

}