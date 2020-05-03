package com.szlachta.medialibrary.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.szlachta.medialibrary.ui.ItemTypeEnum

class BottomNavigationPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = ItemTypeEnum.values().size

    override fun createFragment(position: Int): Fragment {
        val fragment = BottomNavigationItemFragment()
        fragment.arguments = Bundle().apply {
            putSerializable(ItemTypeEnum.ARG, ItemTypeEnum.getByPosition(position))
        }
        return fragment
    }
}