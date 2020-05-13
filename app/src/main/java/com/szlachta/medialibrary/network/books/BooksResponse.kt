package com.szlachta.medialibrary.network.books

import com.google.gson.annotations.SerializedName
import com.szlachta.medialibrary.model.Item
import com.szlachta.medialibrary.model.ItemTypeEnum
import com.szlachta.medialibrary.model.ItemsList
import java.util.stream.Collectors

data class BooksResponse(
    @SerializedName("items") val items: List<BookResponse>? = null,
    @SerializedName("totalItems") val totalItems: Int? = null,
    @SerializedName("error") val error: ErrorResponse? = null
) {

    data class BookResponse(
        @SerializedName("id") val id: String?,
        @SerializedName("volumeInfo") val volumeInfo: VolumeInfoResponse?
    ) {
        fun toModel(): Item {
            return Item(
                title = volumeInfo?.title ?: "",
                type = ItemTypeEnum.BOOKS,
                remoteId = id,
                year = volumeInfo?.publishedDate?.substring(0, 4)?.toInt(),
                imageUrl = volumeInfo?.imageLinks?.thumbnail?.replace("http", "https")
            )
        }
    }

    data class VolumeInfoResponse(
        @SerializedName("title") val title: String?,
        @SerializedName("publishedDate") val publishedDate: String?,
        @SerializedName("imageLinks") val imageLinks: ImageLinksResponse?
    )

    data class ImageLinksResponse(
        @SerializedName("thumbnail") val thumbnail: String?
    )

    data class ErrorResponse(
        @SerializedName("message") val message: String?
    )

    fun toModel(): ItemsList {
        return ItemsList(
            errorMessage = error?.message,
            totalElements = totalItems,
            items = items?.stream()
                ?.map {
                    return@map it.toModel()
                }
                ?.collect(Collectors.toList())
        )
    }
}