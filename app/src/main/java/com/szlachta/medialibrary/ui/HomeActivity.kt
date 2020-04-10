package com.szlachta.medialibrary.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.ui.books.BooksFragment
import com.szlachta.medialibrary.ui.form.FormActivity
import com.szlachta.medialibrary.ui.games.GamesFragment
import com.szlachta.medialibrary.ui.movies.MoviesFragment
import com.szlachta.medialibrary.ui.profile.ProfileActivity
import com.szlachta.medialibrary.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_home)

        setSupportActionBar(action_bar_home)
        action_bar_home.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
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
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            else -> false
        }
    }

    private fun handleBottomTabSelection(actionId: Int): Boolean = when (actionId) {
        R.id.action_games -> {
            supportActionBar?.title = getString(R.string.search_games)
            supportFragmentManager.beginTransaction()
// TODO: .setCustomAnimations()
// TODO: show / hide instead of replace
                .replace(R.id.fragment_container, GamesFragment())
                .commit()
            true
        }
        R.id.action_movies -> {
            supportActionBar?.title = getString(R.string.search_movies)
            supportFragmentManager.beginTransaction()
// TODO: .setCustomAnimations()
// TODO: show / hide instead of replace
                .replace(R.id.fragment_container, MoviesFragment())
                .commit()
            true
        }
        R.id.action_books -> {
            supportActionBar?.title = getString(R.string.search_books)
            supportFragmentManager.beginTransaction()
// TODO: .setCustomAnimations()
// TODO: show / hide instead of replace
                .replace(R.id.fragment_container, BooksFragment())
                .commit()
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
