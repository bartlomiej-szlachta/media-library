package com.szlachta.medialibrary.model

open class Item (
    open val title: String,
    open val firebaseId: String? = null,
    open val remoteId: String? = null,
    open val year: Int? = null,
    open val imageUrl: String? = null
)