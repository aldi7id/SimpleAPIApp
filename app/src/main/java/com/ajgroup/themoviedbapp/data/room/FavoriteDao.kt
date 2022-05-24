package com.ajgroup.themoviedbapp.data.room

import androidx.room.*
import com.ajgroup.themoviedbapp.data.room.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteEntity")
    suspend fun readFavorites(): List<FavoriteEntity>

    @Query("SELECT * FROM FavoriteEntity WHERE id=:id")
    fun readFavoriteById(id: Int): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity):Long

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity):Int
}