package com.szlachta.medialibrary.network.movies

import androidx.lifecycle.MutableLiveData
import com.szlachta.medialibrary.model.ItemsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository private constructor() {
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
    private lateinit var moviesList: MutableLiveData<ItemsList>

    fun getMoviesByQuery(query: String): MutableLiveData<ItemsList> {
        moviesList = MutableLiveData()
        api.getMoviesByQuery(query).enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.code() == 200) {
                    moviesList.value = response.body()?.toModel()
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                moviesList.value = ItemsList(errorMessage = t.message)
            }
        })
        return moviesList
    }
}