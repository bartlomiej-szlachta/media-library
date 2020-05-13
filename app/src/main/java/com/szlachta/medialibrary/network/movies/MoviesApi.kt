package com.szlachta.medialibrary.network.movies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("/")
    fun getMoviesByQuery(@Query("s") query: String): Call<MoviesResponse>
}