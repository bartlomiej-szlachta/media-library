package com.szlachta.medialibrary.network.movies

import com.google.gson.annotations.SerializedName
import com.szlachta.medialibrary.model.Item
import com.szlachta.medialibrary.model.ItemTypeEnum

data class MovieResponse(
    @SerializedName("imdbID") override val remoteId: String,
    @SerializedName("Title") override val title: String,
    @SerializedName("Year") override val year: Int,
    @SerializedName("Poster") override val imageUrl: String
) : Item(
    title = title,
    type = ItemTypeEnum.MOVIES,
    remoteId = remoteId,
    year = year,
    imageUrl = imageUrl
)