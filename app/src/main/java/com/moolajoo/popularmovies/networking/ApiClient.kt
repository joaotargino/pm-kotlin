package com.moolajoo.popularmovies.networking

import com.moolajoo.popularmovies.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by joaopaulotargino on 2018-03-02.
 */
object ApiClient {

    fun create(): TMDBApiService {

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        return retrofit.create(TMDBApiService::class.java)
    }
}