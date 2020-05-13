package com.szlachta.medialibrary.network.books

import androidx.lifecycle.MutableLiveData
import com.szlachta.medialibrary.model.ItemsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksRepository private constructor() {
    companion object {
        private var repository: BooksRepository? = null

        fun getInstance(): BooksRepository {
            if (repository == null) {
                repository = BooksRepository()
            }
            return repository!!
        }
    }

    private var api: BooksApi = RetrofitService.createService()
    private lateinit var booksList: MutableLiveData<ItemsList>

    fun getBooksByQuery(query: String): MutableLiveData<ItemsList> {
        booksList = MutableLiveData()
        api.getBooksByQuery(query).enqueue(object : Callback<BooksResponse> {
            override fun onResponse(
                call: Call<BooksResponse>,
                response: Response<BooksResponse>
            ) {
                if (response.code() == 200) {
                    booksList.value = response.body()?.toItemsResponse()
                }
            }

            override fun onFailure(call: Call<BooksResponse>, t: Throwable) {
                booksList.value = ItemsList(errorMessage = t.message)
            }
        })
        return booksList
    }
}