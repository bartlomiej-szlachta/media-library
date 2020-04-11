package com.szlachta.medialibrary.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
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

        // on keyboard confirm button - remove focus, hide keyboard
        input_search_query.setOnEditorActionListener { _, _, _ ->
            onFinishTyping()
            true
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
                onStartTyping()
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

    private fun onStartTyping() {
        if (currentFocus != null) {
            return
        }
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        input_search_query.requestFocus()
    }

    private fun onFinishTyping() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        input_search_query.clearFocus()
    }
}
