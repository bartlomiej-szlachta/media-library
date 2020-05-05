package com.szlachta.medialibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szlachta.medialibrary.model.ItemTypeEnum
import com.szlachta.medialibrary.model.ListResponse
import com.szlachta.medialibrary.network.movies.MoviesRepository

class SearchViewModel : ViewModel() {
    private val repository: MoviesRepository = MoviesRepository.getInstance()

    fun getItemsList(query: String, itemType: ItemTypeEnum): LiveData<ListResponse> {
        return when (itemType) {
            ItemTypeEnum.GAMES -> MutableLiveData() // TODO
            ItemTypeEnum.MOVIES -> repository.getItemsList(query)
            ItemTypeEnum.BOOKS -> MutableLiveData() // TODO
        }
    }
}