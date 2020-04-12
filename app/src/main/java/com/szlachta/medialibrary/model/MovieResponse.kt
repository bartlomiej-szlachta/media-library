package com.szlachta.medialibrary.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: Int,
    @SerializedName("imdbID") val id: String,
    @SerializedName("Poster") val imageUrl: String
)