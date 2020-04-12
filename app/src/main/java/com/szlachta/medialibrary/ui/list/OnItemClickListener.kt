package com.szlachta.medialibrary.ui.list

import com.szlachta.medialibrary.model.Item

interface OnItemClickListener {
    fun onItemClicked(item: Item)
}