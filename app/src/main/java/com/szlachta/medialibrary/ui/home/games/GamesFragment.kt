package com.szlachta.medialibrary.ui.home.games

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.ui.ItemStatusEnum
import com.szlachta.medialibrary.ui.home.tabs.TabsPagerAdapter
import kotlinx.android.synthetic.main.fragment_games.*

class GamesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager_games.adapter = TabsPagerAdapter(this)
        TabLayoutMediator(tabs_games, pager_games) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String = when (position) {
        ItemStatusEnum.PLANNED.position -> getString(R.string.tab_games_planned)
        ItemStatusEnum.IN_PROGRESS.position -> getString(R.string.tab_games_in_progress)
        ItemStatusEnum.FINISHED.position -> getString(R.string.tab_games_finished)
        else -> ""
    }

}
