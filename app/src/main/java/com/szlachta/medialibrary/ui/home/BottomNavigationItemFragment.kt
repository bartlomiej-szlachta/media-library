package com.szlachta.medialibrary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.model.ItemTypeEnum
import kotlinx.android.synthetic.main.fragment_bottom_navigation_item.pager_tabs
import kotlinx.android.synthetic.main.fragment_bottom_navigation_item.tabs_navigation

class BottomNavigationItemFragment : Fragment() {
    private lateinit var itemType: ItemTypeEnum

    // TODO: restore chosen tab on application go back from background

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {}

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabSelected(tab: TabLayout.Tab?) {
            pager_tabs.setCurrentItem(tab!!.position, false)
        }
    }

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
        pager_tabs.adapter = TabsPagerAdapter(this, itemType)
        pager_tabs.isUserInputEnabled = false
        tabs_navigation.addOnTabSelectedListener(onTabSelectedListener)
    }
}
