package com.ajgroup.themoviedbapp.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajgroup.themoviedbapp.database.Favorite
import com.ajgroup.themoviedbapp.database.FavoriteRepository
import com.ajgroup.themoviedbapp.database.RegisterRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {
    private val _allFavorites: MutableLiveData<List<Favorite?>> = MutableLiveData()
    val allFavorites: LiveData<List<Favorite?>> = _allFavorites

    fun getAllFavorites(){
        viewModelScope.launch {
            val allFavorites = repository.getAllFavorites()
            _allFavorites.postValue(allFavorites)
        }
    }

}