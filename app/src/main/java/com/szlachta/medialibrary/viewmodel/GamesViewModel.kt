package com.szlachta.medialibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szlachta.medialibrary.model.ListResponse

class GamesViewModel : ViewModel(), SearchViewModel {

    private lateinit var games: LiveData<ListResponse>

    override fun getItemsList(query: String): LiveData<ListResponse> {
        // TODO: get games from repository
        games = MutableLiveData()
        return games
    }
}