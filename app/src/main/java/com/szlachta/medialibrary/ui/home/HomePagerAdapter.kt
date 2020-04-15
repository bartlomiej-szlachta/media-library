package com.szlachta.medialibrary.ui.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.szlachta.medialibrary.ui.ItemTypeEnum
import com.szlachta.medialibrary.ui.home.books.BooksFragment
import com.szlachta.medialibrary.ui.home.games.GamesFragment
import com.szlachta.medialibrary.ui.home.movies.MoviesFragment

class HomePagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = ItemTypeEnum.values().size

    override fun createFragment(position: Int): Fragment = when (position) {
        ItemTypeEnum.GAMES.position -> GamesFragment()
        ItemTypeEnum.MOVIES.position -> MoviesFragment()
        ItemTypeEnum.BOOKS.position -> BooksFragment()
        else -> Fragment()
    }
}