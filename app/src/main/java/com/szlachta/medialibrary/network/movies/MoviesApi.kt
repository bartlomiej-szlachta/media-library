package com.szlachta.medialibrary.network.movies

import com.szlachta.medialibrary.model.MoviesListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("/")
    fun getMoviesList(@Query("s") movieQuery: String): Call<MoviesListResponse>
}