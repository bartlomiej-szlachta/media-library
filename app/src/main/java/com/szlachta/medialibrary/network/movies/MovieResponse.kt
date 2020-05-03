package com.szlachta.medialibrary.network.movies

import com.google.gson.annotations.SerializedName
import com.szlachta.medialibrary.model.Item

data class MovieResponse(
    override val firebaseId: String?,
    @SerializedName("imdbID") override val remoteId: String,
    @SerializedName("Title") override val title: String,
    @SerializedName("Year") override val year: Int,
    @SerializedName("Poster") override val imageUrl: String
) : Item