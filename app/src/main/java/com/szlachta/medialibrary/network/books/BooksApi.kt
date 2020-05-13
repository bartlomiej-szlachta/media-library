package com.szlachta.medialibrary.network.books

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("/books/v1/volumes")
    fun getBooksByQuery(@Query("q") query: String): Call<BooksResponse>
}