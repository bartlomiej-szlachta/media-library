package com.szlachta.medialibrary.ui.home.movies

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.szlachta.medialibrary.ui.ItemStatusEnum

class MoviesPagerAdapter(container: Fragment) : FragmentStateAdapter(container) {
    override fun getItemCount(): Int = ItemStatusEnum.values().size

    override fun createFragment(position: Int): Fragment = when (position) {
        ItemStatusEnum.PLANNED.position -> MoviesPlannedFragment()
        ItemStatusEnum.IN_PROGRESS.position -> MoviesInProgressFragment()
        ItemStatusEnum.FINISHED.position -> MoviesFinishedFragment()
        else -> Fragment()
    }
}