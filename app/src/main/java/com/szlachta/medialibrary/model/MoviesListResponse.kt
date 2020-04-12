package com.szlachta.medialibrary.model

import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
    @SerializedName("Search") val list: List<MovieResponse>? = null,
    @SerializedName("totalResults") val totalElements: Int? = null,
    @SerializedName("Error") val errorMessage: String? = null
)