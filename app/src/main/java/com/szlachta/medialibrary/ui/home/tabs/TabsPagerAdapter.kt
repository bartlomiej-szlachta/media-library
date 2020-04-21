package com.szlachta.medialibrary.ui.home.tabs

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.szlachta.medialibrary.ui.ItemStatusEnum

class TabsPagerAdapter(container: Fragment) : FragmentStateAdapter(container) {
    override fun getItemCount(): Int = ItemStatusEnum.values().size

    override fun createFragment(position: Int): Fragment = when (position) {
        ItemStatusEnum.PLANNED.position -> TabPlannedFragment()
        ItemStatusEnum.IN_PROGRESS.position -> TabInProgressFragment()
        ItemStatusEnum.FINISHED.position -> TabFinishedFragment()
        else -> Fragment()
    }
}