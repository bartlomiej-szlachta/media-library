package com.szlachta.medialibrary.network.books

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
                .addQueryParameter("key", BuildConfig.API_KEY_BOOKS)
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
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createService(): BooksApi {
        return retrofit.create(BooksApi::class.java)
    }
}