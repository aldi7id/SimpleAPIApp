package com.ajgroup.themoviedbapp.database


class RegisterRepository(private val dao: RegisterDatabaseDao) {

    suspend fun insert(user: RegisterEntity) {
        return dao.insert(user)
    }

    suspend fun update(user: RegisterEntity) : Int {
        return dao.updateUser(user)
    }

    suspend fun getUserName(userName: String):RegisterEntity?{
        return dao.getUsername(userName)
    }


}