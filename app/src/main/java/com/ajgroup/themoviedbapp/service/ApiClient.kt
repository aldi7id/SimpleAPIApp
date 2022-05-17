package com.ajgroup.themoviedbapp.service

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    fun getInstance(context: Context): ApiService {
        val instace: ApiService by lazy {
         val client = OkHttpClient.Builder()
             .addInterceptor(
                 ChuckerInterceptor.Builder(context)
                     .collector(ChuckerCollector(context))
                     .maxContentLength(2500000L)
                     .redactHeaders(emptySet())
                     .alwaysReadResponseBody(false)
                     .build()
             )
            .addInterceptor(logging)
            .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            retrofit.create(ApiService::class.java)
        }
        return instace
    }
}