package com.szlachta.medialibrary.ui.home.books

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.szlachta.medialibrary.ui.ItemStatusEnum

class BooksPagerAdapter(container: Fragment) : FragmentStateAdapter(container) {
    override fun getItemCount(): Int = ItemStatusEnum.values().size

    override fun createFragment(position: Int): Fragment = when (position) {
        ItemStatusEnum.PLANNED.position -> BooksPlannedFragment()
        ItemStatusEnum.IN_PROGRESS.position -> BooksInProgressFragment()
        ItemStatusEnum.FINISHED.position -> BooksFinishedFragment()
        else -> Fragment()
    }
}