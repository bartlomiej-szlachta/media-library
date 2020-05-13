package com.szlachta.medialibrary.network.tvshows

import androidx.lifecycle.MutableLiveData
import com.szlachta.medialibrary.model.ItemsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.stream.Collectors

class TvShowsRepository private constructor() {
    companion object {
        private var repository: TvShowsRepository? = null

        fun getInstance(): TvShowsRepository {
            if (repository == null) {
                repository = TvShowsRepository()
            }
            return repository!!
        }
    }

    private var api: TvShowsApi = RetrofitService.createService()
    private lateinit var tvShowsList: MutableLiveData<ItemsList>

    fun getTvShowsByQuery(query: String): MutableLiveData<ItemsList> {
        tvShowsList = MutableLiveData()
        api.getTvShowsByQuery(query).enqueue(object : Callback<List<TvShowResponse>> {
            override fun onResponse(
                call: Call<List<TvShowResponse>>,
                response: Response<List<TvShowResponse>>
            ) {
                if (response.code() == 200) {
                    val items = response.body()
                        ?.stream()
                        ?.map {
                            return@map it.toModel()
                        }
                        ?.collect(Collectors.toList())
                    tvShowsList.value = ItemsList(items)
                }
            }

            override fun onFailure(call: Call<List<TvShowResponse>>, t: Throwable) {
                tvShowsList.value = ItemsList(errorMessage = t.message)
            }
        })
        return tvShowsList
    }
}