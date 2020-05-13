package com.szlachta.medialibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szlachta.medialibrary.model.ItemTypeEnum
import com.szlachta.medialibrary.model.ItemsList
import com.szlachta.medialibrary.network.books.BooksRepository
import com.szlachta.medialibrary.network.movies.MoviesRepository

class SearchViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository by lazy {
        MoviesRepository.getInstance()
    }
    private val booksRepository: BooksRepository by lazy {
        BooksRepository.getInstance()
    }

    fun getByQuery(query: String, itemType: ItemTypeEnum): LiveData<ItemsList> {
        return when (itemType) {
            ItemTypeEnum.GAMES -> MutableLiveData() // TODO
            ItemTypeEnum.MOVIES -> moviesRepository.getMoviesByQuery(query)
            ItemTypeEnum.BOOKS -> booksRepository.getBooksByQuery(query)
        }
    }
}