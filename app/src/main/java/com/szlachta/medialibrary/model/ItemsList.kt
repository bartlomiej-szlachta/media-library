package com.szlachta.medialibrary.model

open class ItemsList(
    open val items: List<Item>? = null,
    open val totalElements: Int? = null,
    open val errorMessage: String? = null
)