package com.szlachta.medialibrary.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.szlachta.medialibrary.ui.ItemStatusEnum
import com.szlachta.medialibrary.ui.ItemTypeEnum

class TabsPagerAdapter(
    container: Fragment,
    private val itemType: ItemTypeEnum
) : FragmentStateAdapter(container) {
    override fun getItemCount(): Int = ItemStatusEnum.values().size

    override fun createFragment(position: Int): Fragment {
        return TabItemFragment(itemType, ItemStatusEnum.getByPosition(position))
    }
}