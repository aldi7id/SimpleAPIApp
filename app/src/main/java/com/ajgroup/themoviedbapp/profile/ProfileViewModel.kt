package com.ajgroup.themoviedbapp.profile

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.ajgroup.themoviedbapp.database.entity.RegisterEntity
import com.ajgroup.themoviedbapp.repository.RegisterRepository
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: RegisterRepository, application: Application) :
AndroidViewModel(application), Observable{
    private val _profileViewModel: MutableLiveData<RegisterEntity?> = MutableLiveData()
    val profileViewModel: LiveData<RegisterEntity?> = _profileViewModel


    suspend fun submitUpdate(user: RegisterEntity): Int {
        return repository.update(user)
    }
    fun getUserName(userName: String){
        viewModelScope.launch {
            val newUser = repository.getUserName(userName)
            _profileViewModel.postValue(newUser)
        }

    }
    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    fun doneNavigating(){
        _navigateto.value=false
    }
    val emailpreferences = repository.getEmail()

    fun deletePref() = viewModelScope.launch {
        repository.deletepref()
    }
    fun setEmailPreferences(email: String){
        viewModelScope.launch {
            repository.setEmail(email)
        }
    }



    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}