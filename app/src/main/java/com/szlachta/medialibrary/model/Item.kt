package com.szlachta.medialibrary.model

import java.io.Serializable

open class Item(
    open val title: String,
    open val firebaseId: String? = null,
    open val remoteId: String? = null,
    open val year: Int? = null,
    open val imageUrl: String? = null,
    open val status: ItemStatusEnum? = null
) : Serializable