package com.szlachta.medialibrary.ui.home.games

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.szlachta.medialibrary.ui.ItemStatusEnum

class GamesPagerAdapter(container: Fragment) : FragmentStateAdapter(container) {
    override fun getItemCount(): Int = ItemStatusEnum.values().size

    override fun createFragment(position: Int): Fragment = when (position) {
        ItemStatusEnum.PLANNED.position -> GamesPlannedFragment()
        ItemStatusEnum.IN_PROGRESS.position -> GamesInProgressFragment()
        ItemStatusEnum.FINISHED.position -> GamesFinishedFragment()
        else -> Fragment()
    }
}