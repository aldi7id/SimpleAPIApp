package com.ajgroup.themoviedbapp.data.room

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    val getEmail: Flow<String> = context.counterDataStore.data
        .map {  preferences ->
            preferences[emailKey] ?:""
        }
    val getNama: Flow<String> = context.counterDataStore.data
        .map {  preferences ->
            preferences[namaKey] ?:""
        }
    suspend fun setEmail(email: String){
        context.counterDataStore.edit {
            it[emailKey] = email
        }
    }
    suspend fun setNama(nama: String){
        context.counterDataStore.edit {
            it[namaKey] = nama
        }
    }
    suspend fun deletepref(){
        context.counterDataStore.edit {
            it.clear()
        }
    }
    companion object {
        private const val DATASTORE_NAME = "counter_preferences"
        private const val EMAIL_KEY = "email_key"
        private const val NAMA_KEY = "nama_key"
        val namaKey = stringPreferencesKey(NAMA_KEY)
        val emailKey = stringPreferencesKey(EMAIL_KEY)


        private val Context.counterDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}