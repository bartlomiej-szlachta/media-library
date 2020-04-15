package com.szlachta.medialibrary.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.model.Item
import com.szlachta.medialibrary.ui.ItemTypeEnum
import com.szlachta.medialibrary.ui.home.HomeActivity
import com.szlachta.medialibrary.ui.list.ImageLoader
import com.szlachta.medialibrary.ui.list.ListAdapter
import com.szlachta.medialibrary.ui.list.OnItemClickListener
import com.szlachta.medialibrary.viewmodel.MoviesViewModel
import com.szlachta.medialibrary.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), ImageLoader, OnItemClickListener {

    private lateinit var viewModel: SearchViewModel
    private var isClearIconVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_search)

        setSupportActionBar(action_bar_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val currentItem =
            intent.getSerializableExtra(HomeActivity.CURRENT_ITEM_EXTRA) as ItemTypeEnum
        initializeViewModel(currentItem)
        setHint(currentItem)

        rv_search_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_search_list.adapter = ListAdapter(emptyList(), this, this)

        onStartTyping()

        // on text changed - show / hide clear icon
        input_search_query.addTextChangedListener {
            onType()
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
        onFinishTyping()
        finish()
        return true
    }

    override fun loadImage(url: String?, into: ImageView) {
        if (url != null) {
            Glide.with(this).load(url).into(into)
        }
    }

    override fun onItemClicked(item: Item) {
        // TODO: on item clicked
        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
    }

    private fun initializeViewModel(itemType: ItemTypeEnum) {
        viewModel = when (itemType) {
            ItemTypeEnum.MOVIES -> ViewModelProvider(this).get(MoviesViewModel::class.java)
            else -> {
                // TODO: implement other data types search
                onFinishTyping()
                Toast.makeText(this, "Try searching movies", Toast.LENGTH_SHORT).show()
                finish()
                ViewModelProvider(this).get(MoviesViewModel::class.java)
            }
        }
    }

    private fun setHint(itemType: ItemTypeEnum) {
        input_search_query.hint = when (itemType) {
            ItemTypeEnum.GAMES -> getString(R.string.search_games)
            ItemTypeEnum.MOVIES -> getString(R.string.search_movies)
            ItemTypeEnum.BOOKS -> getString(R.string.search_books)
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

    private fun onType() {
        if (input_search_query.text.isNotEmpty()) {

            // show clear icon if necessary
            if (!isClearIconVisible) {
                invalidateOptionsMenu()
                isClearIconVisible = true
            }

            // request data
            requestMoviesList(input_search_query.text.toString())
        } else {
            invalidateOptionsMenu()
            isClearIconVisible = false
            rv_search_list.adapter = ListAdapter(emptyList(), this, this)
        }
    }

    private fun requestMoviesList(movieQuery: String) {
        viewModel.getItemsList(movieQuery).observe(this,
            Observer { t ->
                if (t.items != null) {
                    rv_search_list.adapter = ListAdapter(t.items!!, this, this)
                } else {
                    Toast.makeText(this, t.errorMessage, Toast.LENGTH_SHORT).show()
                }
            })
    }
}
