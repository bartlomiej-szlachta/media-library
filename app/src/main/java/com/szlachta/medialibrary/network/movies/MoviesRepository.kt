package com.szlachta.medialibrary.network.movies

import androidx.lifecycle.MutableLiveData
import com.szlachta.medialibrary.model.MoviesListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository {
    companion object {
        private var repository: MoviesRepository? = null

        fun getInstance(): MoviesRepository {
            if (repository == null) {
                repository = MoviesRepository()
            }
            return repository!!
        }
    }

    private var api: MoviesApi = RetrofitService.createService()

    private lateinit var moviesList: MutableLiveData<MoviesListResponse>

    fun getMoviesList(movieQuery: String): MutableLiveData<MoviesListResponse> {
        moviesList = MutableLiveData()
        api.getMoviesList(movieQuery).enqueue(object : Callback<MoviesListResponse> {
            override fun onResponse(
                call: Call<MoviesListResponse>,
                response: Response<MoviesListResponse>
            ) {
                if (response.code() == 200) {
                    moviesList.value = response.body()
                }
            }

            override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                moviesList.value = MoviesListResponse(errorMessage = t.message)
            }
        })
        return moviesList
    }
}