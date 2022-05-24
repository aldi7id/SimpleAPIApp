package com.ajgroup.themoviedbapp.data

import androidx.lifecycle.asLiveData
import com.ajgroup.themoviedbapp.data.room.RegisterDatabaseDao
import com.ajgroup.themoviedbapp.data.room.DataStoreManager
import com.ajgroup.themoviedbapp.data.room.RegisterEntity


class RegisterRepository(private val dao: RegisterDatabaseDao, private val pref: DataStoreManager) {
    suspend fun insert(user: RegisterEntity) {
        return dao.insert(user)
    }

    suspend fun update(user: RegisterEntity) : Int {
        return dao.updateUser(user)
    }

    suspend fun getUserName(userName: String): RegisterEntity?{
        return dao.getUsername(userName)
    }

    suspend fun setEmail(email: String) = pref.setEmail(email)

    suspend fun setNama(fullName: String) = pref.setNama(fullName)
    fun getEmail() = pref.getEmail.asLiveData()
    fun getNama() = pref.getNama.asLiveData()
    suspend fun deletepref() = pref.deletepref()


}