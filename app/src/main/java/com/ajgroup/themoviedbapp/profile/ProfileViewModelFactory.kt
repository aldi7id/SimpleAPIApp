package com.ajgroup.themoviedbapp.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajgroup.themoviedbapp.repository.RegisterRepository
import java.lang.IllegalArgumentException

class ProfileViewModelFactory(
    private val repository: RegisterRepository,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}