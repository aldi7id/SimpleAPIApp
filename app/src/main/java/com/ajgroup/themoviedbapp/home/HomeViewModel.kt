package com.ajgroup.themoviedbapp.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ajgroup.themoviedbapp.database.RegisterEntity
import com.ajgroup.themoviedbapp.database.RegisterRepository

class HomeViewModel (private val repository: RegisterRepository, application: Application):AndroidViewModel(application){
    private val _homeViewModel: MutableLiveData<RegisterEntity?> = MutableLiveData()
    val homeViewModel: LiveData<RegisterEntity?> = _homeViewModel
    private val _navigateto = MutableLiveData<Boolean>()
    private val _navigatetofavorite = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto
    val navigatetofavorite: LiveData<Boolean>
        get() = _navigatetofavorite

    fun doneNavigating(){
        _navigateto.value=false
    }
    fun doneNavigatingfavorite(){
        _navigatetofavorite.value=false
    }

    fun backButtonclicked(){
        _navigateto.value = true
    }
    fun favoriteButtonclicked(){
        _navigatetofavorite.value = true
    }
    val emailpref = repository.getEmail()

    suspend fun getUserName(userName: String){
        val newUser = repository.getUserName(userName)
        _homeViewModel.postValue(newUser)
    }

}