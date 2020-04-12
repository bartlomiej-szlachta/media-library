package com.szlachta.medialibrary.network.movies

import com.szlachta.medialibrary.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val retrofit = createRetrofit()

    private fun createRetrofit(): Retrofit {
        val authInterceptor = Interceptor { chain ->
            val newUrl: HttpUrl = chain.request().url
                .newBuilder()
                .addQueryParameter("apikey", BuildConfig.API_KEY_MOVIES)
                .build()
            val newRequest: Request = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }
        val client = OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createService(): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }
}