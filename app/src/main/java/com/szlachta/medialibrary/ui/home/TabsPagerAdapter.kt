package com.szlachta.medialibrary.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.szlachta.medialibrary.model.ItemStatusEnum
import com.szlachta.medialibrary.model.ItemTypeEnum

class TabsPagerAdapter(
    container: Fragment,
    private val itemType: ItemTypeEnum
) : FragmentStateAdapter(container) {

    override fun getItemCount(): Int = ItemStatusEnum.values().size

    override fun createFragment(position: Int): Fragment {
        val fragment = TabItemFragment()
        fragment.arguments = Bundle().apply {
            putSerializable(ItemTypeEnum.TAG, itemType)
            putSerializable(ItemStatusEnum.TAG, ItemStatusEnum.getByPosition(position))
        }
        return fragment
    }
}