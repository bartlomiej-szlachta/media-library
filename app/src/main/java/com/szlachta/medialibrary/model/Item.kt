package com.szlachta.medialibrary.model

interface Item {
    val remoteId: String?
    val title: String
    val year: Int
    val imageUrl: String?
}