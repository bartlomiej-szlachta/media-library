package com.szlachta.medialibrary.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.szlachta.medialibrary.ItemEnum
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.ui.HomeActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private lateinit var currentItem: ItemEnum
    private var isClearIconVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(action_bar_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        currentItem = intent.getSerializableExtra(HomeActivity.CURRENT_ITEM_EXTRA) as ItemEnum
        setHint()

        input_search_query.requestFocus()
        input_search_query.addTextChangedListener {
            if (input_search_query.text.isNotEmpty()) {
                if (!isClearIconVisible) {
                    invalidateOptionsMenu()
                    isClearIconVisible = true
                }
                // TODO: perform query
            } else {
                invalidateOptionsMenu()
                isClearIconVisible = false
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_clear)!!.isVisible = isClearIconVisible
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                input_search_query.setText("")
                true
            }
            else -> false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setHint() {
        input_search_query.hint = when (currentItem) {
            ItemEnum.GAMES -> getString(R.string.search_games)
            ItemEnum.MOVIES -> getString(R.string.search_movies)
            ItemEnum.BOOKS -> getString(R.string.search_books)
        }
    }
}
