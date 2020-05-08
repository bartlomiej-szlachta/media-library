package com.szlachta.medialibrary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.model.ItemStatusEnum
import com.szlachta.medialibrary.model.ItemTypeEnum
import kotlinx.android.synthetic.main.fragment_bottom_navigation_item.pager_items
import kotlinx.android.synthetic.main.fragment_bottom_navigation_item.tabs_items

class BottomNavigationItemFragment : Fragment() {
    private lateinit var itemType: ItemTypeEnum

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_navigation_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments
            ?.takeIf { it.containsKey(ItemTypeEnum.ARG) }
            ?.apply {
                itemType = getSerializable(ItemTypeEnum.ARG) as ItemTypeEnum
            }
        pager_items.adapter = TabsPagerAdapter(this, itemType)
        pager_items.isUserInputEnabled = false
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
