package com.szlachta.medialibrary.network.movies

import com.google.gson.annotations.SerializedName
import com.szlachta.medialibrary.model.ListResponse

data class MoviesListResponse(
    @SerializedName("Search") override val items: List<MovieResponse>? = null,
    @SerializedName("totalResults") override val totalElements: Int? = null,
    @SerializedName("Error") override val errorMessage: String? = null
) : ListResponse(
    items = items,
    totalElements = totalElements,
    errorMessage = errorMessage
)