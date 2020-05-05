package com.szlachta.medialibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.szlachta.medialibrary.firebase.DatabaseRepository
import com.szlachta.medialibrary.model.BasicResponse
import com.szlachta.medialibrary.model.Item
import com.szlachta.medialibrary.model.ItemTypeEnum
import com.szlachta.medialibrary.model.ListResponse

class DatabaseViewModel : ViewModel() {
    private val repository = DatabaseRepository.getInstance()

    fun getItemsList(itemType: ItemTypeEnum): LiveData<ListResponse> {
        return repository.getItemsList(itemType)
    }

    fun saveItem(item: Item, itemType: ItemTypeEnum): LiveData<BasicResponse> {
        return repository.saveItem(item, itemType)
    }
}