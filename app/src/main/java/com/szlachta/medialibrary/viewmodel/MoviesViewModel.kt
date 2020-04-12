package com.szlachta.medialibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.szlachta.medialibrary.network.movies.MoviesRepository
import com.szlachta.medialibrary.model.MoviesListResponse

class MoviesViewModel : ViewModel() {
    private val repository: MoviesRepository = MoviesRepository.getInstance()

    private lateinit var movies: LiveData<MoviesListResponse>

    fun getMoviesList(movieQuery: String): LiveData<MoviesListResponse> {
        movies = repository.getMoviesList(movieQuery)
        return movies
    }
}