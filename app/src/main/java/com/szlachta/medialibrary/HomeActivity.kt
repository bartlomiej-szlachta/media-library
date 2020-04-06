package com.szlachta.medialibrary

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.szlachta.medialibrary.ui.books.BooksFragment
import com.szlachta.medialibrary.ui.games.GamesFragment
import com.szlachta.medialibrary.ui.movies.MoviesFragment
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
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.colorBackground)))
        setContentView(R.layout.activity_home)


        bottom_navigation.setOnNavigationItemSelectedListener {
            if (bottom_navigation.selectedItemId == it.itemId) {
                return@setOnNavigationItemSelectedListener false
            }
            handleBottomTabSelection(it.itemId)
        }

        floating_action_button_add.setOnClickListener {
            // TODO: navigate to the form
        }

//        TODO: log out button on profile dialog
//        sign_out.setOnClickListener {
//            signOut()
//        }
//        setDataOnView()
    }

    override fun onResume() {
        super.onResume()
        handleBottomTabSelection(bottom_navigation.selectedItemId)
    }

//    private fun setDataOnView() {
//        val firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
//
//        Glide.with(this)
//            .load(firebaseUser.photoUrl)
//            .into(findViewById(R.id.profile_image))
//        profile_text.text = firebaseUser.displayName
//        profile_email.text = firebaseUser.email
//    }
//
//    private fun signOut() {
//        startActivity(SignInActivity.getLaunchIntent(this))
//        FirebaseAuth.getInstance().signOut()
//        finish()
//    }

    private fun handleBottomTabSelection(actionId: Int): Boolean = when (actionId) {
        R.id.action_games -> {
            title = getString(R.string.search_games)
            supportFragmentManager.beginTransaction()
// TODO: .setCustomAnimations()
// TODO: show / hide instead of replace
                .replace(R.id.fragment_container, GamesFragment())
                .commit()
            true
        }
        R.id.action_movies -> {
            title = getString(R.string.search_movies)
            supportFragmentManager.beginTransaction()
// TODO: .setCustomAnimations()
// TODO: show / hide instead of replace
                .replace(R.id.fragment_container, MoviesFragment())
                .commit()
            true
        }
        R.id.action_books -> {
            title = getString(R.string.search_books)
            supportFragmentManager.beginTransaction()
// TODO: .setCustomAnimations()
// TODO: show / hide instead of replace
                .replace(R.id.fragment_container, BooksFragment())
                .commit()
            true
        }
        else -> false
    }
}
