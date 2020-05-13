package com.szlachta.medialibrary.network.tvshows

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowsApi {
    @GET("/search/shows")
    fun getTvShowsByQuery(@Query("q") query: String): Call<List<TvShowResponse>>
}