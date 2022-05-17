package com.ajgroup.themoviedbapp.register

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ajgroup.themoviedbapp.database.entity.RegisterEntity
import com.ajgroup.themoviedbapp.repository.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RegisterRepository, application: Application) :
    AndroidViewModel(application), Observable {


    var userDetailsLiveData = MutableLiveData<Array<RegisterEntity>>()


    val inputFirstName = MutableLiveData<String?>()


    val inputLastName = MutableLiveData<String?>()


    val inputUsername = MutableLiveData<String?>()


    val inputPassword = MutableLiveData<String?>()


    val inputRePassword = MutableLiveData<String?>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    private val _errorToast = MutableLiveData<Boolean>()

    val errotoast: LiveData<Boolean>
        get() = _errorToast

    private val _successToast = MutableLiveData<Boolean>()

    val successtoast: LiveData<Boolean>
        get() = _successToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    private val _errorToastPassword = MutableLiveData<Boolean>()

    val errotoastPassword: LiveData<Boolean>
        get() = _errorToastPassword


    fun sumbitButton() {
        if (inputFirstName.value == null || inputLastName.value == null || inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                val usersNames = repository.getUserName(inputUsername.value!!)
                if (usersNames != null) {
                    _errorToastUsername.value = true
                }
                when {
                    inputPassword.value != inputRePassword.value -> {
                        _errorToastPassword.value = true
                    }
                    else -> {
                        val firstName = inputFirstName.value!!
                        val lastName = inputLastName.value!!
                        val email = inputUsername.value!!
                        val password = inputPassword.value!!
                        insert(RegisterEntity(0, firstName, lastName, email, password,""))
                        inputFirstName.value = null
                        inputLastName.value = null
                        inputUsername.value = null
                        inputPassword.value = null
                        _successToast.value = true
                        _navigateto.value = true
                }
                }

            }
        }
    }


    fun doneNavigating() {
        _navigateto.value = false
    }

    fun donetoast() {
        _errorToast.value = false
    }

    fun donetoastUserName() {
        _errorToast.value = false
    }

    private fun insert(user: RegisterEntity): Job = viewModelScope.launch {
        repository.insert(user)
    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}