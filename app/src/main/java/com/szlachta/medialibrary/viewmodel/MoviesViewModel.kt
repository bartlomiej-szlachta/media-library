package com.szlachta.medialibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.szlachta.medialibrary.model.ListResponse
import com.szlachta.medialibrary.network.movies.MoviesRepository

class MoviesViewModel : ViewModel(), SearchViewModel {
    private val repository: MoviesRepository = MoviesRepository.getInstance()

    private lateinit var movies: LiveData<ListResponse>

    override fun getItemsList(query: String): LiveData<ListResponse> {
        movies = repository.getItemsList(query)
        return movies
    }
}