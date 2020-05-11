package com.szlachta.medialibrary.model

import java.io.Serializable

open class Item(
    open val title: String,
    open var type: ItemTypeEnum?,
    open var firebaseId: String? = null,
    open val remoteId: String? = null,
    open val year: Int? = null,
    open val imageUrl: String? = null,
    open var status: ItemStatusEnum? = null
) : Serializable