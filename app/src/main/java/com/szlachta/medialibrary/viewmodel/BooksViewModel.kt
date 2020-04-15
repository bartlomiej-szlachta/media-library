package com.szlachta.medialibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szlachta.medialibrary.model.ListResponse

class BooksViewModel : ViewModel(), SearchViewModel {

    private lateinit var books: LiveData<ListResponse>

    override fun getItemsList(query: String): LiveData<ListResponse> {
        // TODO: get books from repository
        books = MutableLiveData()
        return books
    }
}