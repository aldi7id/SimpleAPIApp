package com.ajgroup.themoviedbapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val overview: String,
    val posterPath: String?,
    val title: String = "",
    val voteAverage: Double
)