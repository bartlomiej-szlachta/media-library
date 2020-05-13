package com.szlachta.medialibrary.network.tvshows

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val retrofit = createRetrofit()

    private fun createRetrofit(): Retrofit {
        val client = OkHttpClient().newBuilder().build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.tvmaze.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createService(): TvShowsApi {
        return retrofit.create(TvShowsApi::class.java)
    }
}