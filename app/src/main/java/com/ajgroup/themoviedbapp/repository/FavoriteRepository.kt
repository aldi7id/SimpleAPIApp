package com.ajgroup.themoviedbapp.repository

import com.ajgroup.themoviedbapp.dao.FavoriteDao

class FavoriteRepository (private val favoriteDao: FavoriteDao){
    suspend fun getAllFavorites() = favoriteDao.readFavorites()

}