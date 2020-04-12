package com.szlachta.medialibrary.network

import androidx.lifecycle.LiveData
import com.szlachta.medialibrary.model.ListResponse

interface SearchRepository {
    fun getItemsList(query: String): LiveData<ListResponse>
}