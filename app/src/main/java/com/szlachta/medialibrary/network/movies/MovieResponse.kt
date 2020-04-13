package com.szlachta.medialibrary.network.movies

import com.google.gson.annotations.SerializedName
import com.szlachta.medialibrary.model.Item

data class MovieResponse(
    @SerializedName("imdbID") override val id: String,
    @SerializedName("Title") override val title: String,
    @SerializedName("Year") override val year: Int,
    @SerializedName("Poster") override val imageUrl: String
) : Item