package com.szlachta.medialibrary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.ui.ItemStatusEnum
import com.szlachta.medialibrary.ui.ItemTypeEnum

class TabItemFragment(
    private val itemType: ItemTypeEnum,
    private val itemStatus: ItemStatusEnum
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_item, container, false)
    }
}
