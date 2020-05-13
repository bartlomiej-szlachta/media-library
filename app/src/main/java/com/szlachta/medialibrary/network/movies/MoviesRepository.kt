package com.szlachta.medialibrary.network.movies

import androidx.lifecycle.MutableLiveData
import com.szlachta.medialibrary.model.ItemTypeEnum
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
        api.getMoviesByQuery(query).enqueue(object : Callback<MoviesListResponse> {
            override fun onResponse(
                call: Call<MoviesListResponse>,
                response: Response<MoviesListResponse>
            ) {
                if (response.code() == 200) {
                    moviesList.value = response.body()

                    // TODO: set the type better way
                    moviesList.value?.items?.forEach {
                        it.type = ItemTypeEnum.MOVIES
                    }
                }
            }

            override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                moviesList.value = MoviesListResponse(errorMessage = t.message)
            }
        })
        return moviesList
    }
}