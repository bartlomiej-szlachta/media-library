package com.szlachta.medialibrary.network.tvshows

import com.google.gson.annotations.SerializedName
import com.szlachta.medialibrary.model.Item
import com.szlachta.medialibrary.model.ItemTypeEnum

data class TvShowResponse(
    @SerializedName("show") val show: ShowResponse? = null
) {

    data class ShowResponse(
        @SerializedName("id") val id: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("premiered") val premiered: String,
        @SerializedName("image") val image: ImageResponse
    )

    data class ImageResponse(
        @SerializedName("medium") val medium: String?
    )

    fun toModel(): Item {
        return Item(
            title = show?.name ?: "",
            type = ItemTypeEnum.TV_SHOWS,
            remoteId = show?.id.toString(),
            year = show?.premiered?.substring(0, 4)?.toInt(),
            imageUrl = show?.image?.medium?.replace("http", "https")
        )
    }
}