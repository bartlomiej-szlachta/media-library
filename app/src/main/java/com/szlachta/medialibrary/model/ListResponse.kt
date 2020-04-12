package com.szlachta.medialibrary.model

interface ListResponse {
    val items: List<Item>?
    val totalElements: Int?
    val errorMessage: String?
}