package com.szlachta.medialibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.szlachta.medialibrary.firebase.DatabaseRepository
import com.szlachta.medialibrary.model.*

class DatabaseViewModel : ViewModel() {
    private val repository = DatabaseRepository.getInstance()

    fun getItemsList(itemType: ItemTypeEnum, itemStatus: ItemStatusEnum): LiveData<ListResponse> {
        return repository.getItemsList(itemType, itemStatus)
    }

    fun saveItem(item: Item, itemType: ItemTypeEnum): LiveData<BasicResponse> {
        return repository.saveItem(item, itemType)
    }

    fun updateStatus(
        item: Item,
        itemType: ItemTypeEnum,
        newStatus: ItemStatusEnum
    ): LiveData<BasicResponse> {
        return repository.updateStatus(item, itemType, newStatus)
    }
}