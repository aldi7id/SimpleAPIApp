package com.ajgroup.themoviedbapp.profile

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.ajgroup.themoviedbapp.database.RegisterEntity
import com.ajgroup.themoviedbapp.database.RegisterRepository
import com.ajgroup.themoviedbapp.model.DetailMovieResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: ProfileRepository, application: Application) :
AndroidViewModel(application), Observable{
    private val _profileViewModel: MutableLiveData<RegisterEntity?> = MutableLiveData()
    val profileViewModel: LiveData<RegisterEntity?> = _profileViewModel

    val users = repository.users
    init {
        Log.i("MYTAG","inside_users_Lisrt_init")
    }

    suspend fun submitUpdate(user: RegisterEntity): Int {
        return repository.update(user)
    }
    suspend fun getUserName(userName: String){
        val newUser = repository.getUserName(userName)
        _profileViewModel.postValue(newUser)
    }
    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    fun doneNavigating(){
        _navigateto.value=false
    }

    fun backButtonclicked(){
        _navigateto.value = true
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}