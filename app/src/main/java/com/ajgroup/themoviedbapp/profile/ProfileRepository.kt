package com.ajgroup.themoviedbapp.profile

import android.util.Log
import com.ajgroup.themoviedbapp.database.RegisterDatabaseDao
import com.ajgroup.themoviedbapp.database.RegisterEntity

class ProfileRepository(private val dao: RegisterDatabaseDao) {

    val users = dao.getAllUsers()
    suspend fun insert(user: RegisterEntity) {
        return dao.insert(user)
    }

    suspend fun update(user: RegisterEntity) : Int {
        return dao.updateUser(user)
    }

    suspend fun getUserName(userName: String): RegisterEntity?{
        Log.i("MYTAG", "inside Repository Getusers fun ")
        return dao.getUsername(userName)
    }
    //suspend fun deleteAll(): Int {
    //    return dao.deleteAll()
    //}

}