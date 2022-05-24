package com.ajgroup.themoviedbapp.ui.login

import android.app.Application
import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.ajgroup.themoviedbapp.data.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: RegisterRepository, application: Application):
    AndroidViewModel(application), Observable {


    val inputUsername = MutableLiveData<String?>()
    val inputPassword = MutableLiveData<String?>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _navigatetoRegister = MutableLiveData<Boolean>()
    val navigatetoRegister: LiveData<Boolean>
        get() = _navigatetoRegister

    private val _navigatetoUserDetails = MutableLiveData<Boolean>()
    val navigatetoUserDetails: LiveData<Boolean>
        get() = _navigatetoUserDetails

    private val _errorToast = MutableLiveData<Boolean>()

    val errortoast: LiveData<Boolean>
        get() = _errorToast
    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    private val _errorToastInvalidPassword = MutableLiveData<Boolean>()

    val errorToastInvalidPassword: LiveData<Boolean>
        get() = _errorToastInvalidPassword

    private val _name = MutableLiveData<String>()

    val name: LiveData<String>
        get() = _name

    fun signUP() {
        _navigatetoRegister.value = true
    }
    fun loginButton() {
        if (inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                val usersNames = repository.getUserName(inputUsername.value!!)
                if (usersNames != null) {
                    if(usersNames.passwrd == inputPassword.value){
                        inputUsername.value = null
                        inputPassword.value = null
                        _name.value = "${usersNames.firstName} ${usersNames.lastName}"
                        _navigatetoUserDetails.value = true
                    }else{
                        _errorToastInvalidPassword.value = true
                    }
                } else {
                    _errorToastUsername.value = true
                    _name.value=""
                }
            }
        }
    }
    fun doneNavigatingRegiter() {
        _navigatetoRegister.value = false
    }

    fun doneNavigatingUserDetails() {
        _navigatetoUserDetails.value = false
    }


    fun donetoast() {
        _errorToast.value = false
        Log.i("MYTAG", "Done taoasting ")
    }


    fun donetoastErrorUsername() {
        _errorToastUsername .value = false
        Log.i("MYTAG", "Done taoasting ")
    }

    fun donetoastInvalidPassword() {
        _errorToastInvalidPassword .value = false
        Log.i("MYTAG", "Done taoasting ")
    }

    fun setEmailPreferences(email: String){
        viewModelScope.launch {
            repository.setEmail(email)
        }
    }
    fun setNamaPreferences(nama: String){
        viewModelScope.launch {
            repository.setNama(nama)
        }
    }
    val emailpreferences = repository.getEmail()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}