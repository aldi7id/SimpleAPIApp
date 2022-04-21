package com.ajgroup.themoviedbapp.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ajgroup.themoviedbapp.database.RegisterRepository

class HomeViewModel (private val repository: RegisterRepository, application: Application):AndroidViewModel(application){


    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    fun doneNavigating(){
        _navigateto.value=false
    }

    fun backButtonclicked(){
        _navigateto.value = true
    }

}