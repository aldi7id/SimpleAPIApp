package com.ajgroup.themoviedbapp.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajgroup.themoviedbapp.database.FavoriteEntity
import com.ajgroup.themoviedbapp.database.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {
    private val _allFavorites: MutableLiveData<List<FavoriteEntity?>> = MutableLiveData()
    val allFavorites: LiveData<List<FavoriteEntity?>> = _allFavorites

    fun getAllFavorites(){
        viewModelScope.launch {
            val allFavorites = repository.getAllFavorites()
            _allFavorites.postValue(allFavorites)
        }
    }

}