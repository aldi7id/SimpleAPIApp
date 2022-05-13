package com.ajgroup.themoviedbapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ajgroup.themoviedbapp.dao.FavoriteDao
import com.ajgroup.themoviedbapp.dao.RegisterDatabaseDao
import com.ajgroup.themoviedbapp.database.entity.FavoriteEntity
import com.ajgroup.themoviedbapp.database.entity.RegisterEntity

@Database(entities = [RegisterEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class RegisterDatabase : RoomDatabase() {

    abstract val registerDatabaseDao: RegisterDatabaseDao
    abstract val favoriteDao: FavoriteDao

    companion object {

        @Volatile
        private var INSTANCE: RegisterDatabase? = null


        fun getInstance(context: Context): RegisterDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RegisterDatabase::class.java,
                        "user_details_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}