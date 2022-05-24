package com.ajgroup.themoviedbapp.data

import com.ajgroup.themoviedbapp.data.room.FavoriteDao

class FavoriteRepository (private val favoriteDao: FavoriteDao){
    suspend fun getAllFavorites() = favoriteDao.readFavorites()

}