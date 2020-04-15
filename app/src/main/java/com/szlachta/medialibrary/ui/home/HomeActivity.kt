package com.szlachta.medialibrary.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.ui.ItemTypeEnum
import com.szlachta.medialibrary.ui.SignInActivity
import com.szlachta.medialibrary.ui.form.FormActivity
import com.szlachta.medialibrary.ui.profile.ProfileActivity
import com.szlachta.medialibrary.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    companion object {
        const val CURRENT_ITEM_EXTRA = "current_item_extra"

        fun getLaunchIntent(from: Context) = Intent(from, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    private lateinit var currentItem: ItemTypeEnum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_home)

        pager_home.adapter = HomePagerAdapter(this)

        setSupportActionBar(action_bar_home)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        action_bar_home.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
                .putExtra(CURRENT_ITEM_EXTRA, currentItem)
            startActivity(intent)
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            if (bottom_navigation.selectedItemId == it.itemId) {
                return@setOnNavigationItemSelectedListener false
            }
            handleBottomTabSelection(it.itemId)
        }

        floating_action_button_add.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        handleBottomTabSelection(bottom_navigation.selectedItemId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                startActivityForResult(Intent(this, ProfileActivity::class.java), 0)
                true
            }
            else -> false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ProfileActivity.RC_SIGN_OUT) {
            signOut()
        }
    }

    private fun handleBottomTabSelection(actionId: Int): Boolean = when (actionId) {
        R.id.action_games -> {
            currentItem = ItemTypeEnum.GAMES
            action_bar_home_title.text = getString(R.string.search_games)
            pager_home.currentItem = ItemTypeEnum.GAMES.position
            true
        }
        R.id.action_movies -> {
            currentItem = ItemTypeEnum.MOVIES
            action_bar_home_title.text = getString(R.string.search_movies)
            pager_home.currentItem = ItemTypeEnum.MOVIES.position
            true
        }
        R.id.action_books -> {
            currentItem = ItemTypeEnum.BOOKS
            action_bar_home_title.text = getString(R.string.search_books)
            pager_home.currentItem = ItemTypeEnum.BOOKS.position
            true
        }
        else -> false
    }

    private fun signOut() {
        startActivity(SignInActivity.getLaunchIntent(this))
        FirebaseAuth.getInstance().signOut()
        finish()
    }
}
