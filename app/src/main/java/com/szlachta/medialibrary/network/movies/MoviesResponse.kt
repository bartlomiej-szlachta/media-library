package com.szlachta.medialibrary.network.movies

import com.google.gson.annotations.SerializedName
import com.szlachta.medialibrary.model.Item
import com.szlachta.medialibrary.model.ItemTypeEnum
import com.szlachta.medialibrary.model.ItemsList
import java.util.stream.Collectors

data class MoviesResponse(
    @SerializedName("Search") val items: List<MovieResponse>? = null,
    @SerializedName("totalResults") val totalElements: Int? = null,
    @SerializedName("Error") val errorMessage: String? = null
) {
    data class MovieResponse(
        @SerializedName("imdbID") val remoteId: String?,
        @SerializedName("Title") val title: String?,
        @SerializedName("Year") val year: String?,
        @SerializedName("Poster") val imageUrl: String?
    ) {
        fun toModel(): Item {
            return Item(
                title = title ?: "",
                type = ItemTypeEnum.MOVIES,
                remoteId = remoteId,
                year = year?.substring(0, 4)?.toInt(),
                imageUrl = imageUrl
            )
        }
    }

    fun toModel(): ItemsList {
        return ItemsList(
            items = items?.stream()
                ?.map {
                    return@map it.toModel()
                }
                ?.collect(Collectors.toList()),
            totalElements = totalElements,
            errorMessage = errorMessage
        )
    }
}