package com.szlachta.medialibrary.ui.home.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.ui.ItemStatusEnum
import kotlinx.android.synthetic.main.fragment_books.*

class BooksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager_books.adapter = BooksPagerAdapter(this)
        TabLayoutMediator(tabs_books, pager_books) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String = when (position) {
        ItemStatusEnum.PLANNED.position -> getString(R.string.tab_books_planned)
        ItemStatusEnum.IN_PROGRESS.position -> getString(R.string.tab_books_in_progress)
        ItemStatusEnum.FINISHED.position -> getString(R.string.tab_books_finished)
        else -> ""
    }

}
