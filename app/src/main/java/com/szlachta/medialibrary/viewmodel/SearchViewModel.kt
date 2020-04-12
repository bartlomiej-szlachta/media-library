package com.szlachta.medialibrary.viewmodel

import androidx.lifecycle.LiveData
import com.szlachta.medialibrary.model.ListResponse

interface SearchViewModel {
    fun getItemsList(query: String): LiveData<ListResponse>
}