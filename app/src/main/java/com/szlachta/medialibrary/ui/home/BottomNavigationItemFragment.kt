package com.szlachta.medialibrary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.ui.ItemStatusEnum
import com.szlachta.medialibrary.ui.ItemTypeEnum
import kotlinx.android.synthetic.main.fragment_bottom_navigation_item.pager_items
import kotlinx.android.synthetic.main.fragment_bottom_navigation_item.tabs_items

class BottomNavigationItemFragment(private val itemType: ItemTypeEnum) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_navigation_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager_items.adapter = TabsPagerAdapter(this, itemType)
        TabLayoutMediator(tabs_items, pager_items) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String {
        return when (position) {
            ItemStatusEnum.PLANNED.position -> getString(R.string.tab_planned)
            ItemStatusEnum.IN_PROGRESS.position -> getString(R.string.tab_in_progress)
            ItemStatusEnum.FINISHED.position -> getString(R.string.tab_finished)
            else -> throw RuntimeException("There is no ItemStatusEnum with position $position")
        }
    }


}