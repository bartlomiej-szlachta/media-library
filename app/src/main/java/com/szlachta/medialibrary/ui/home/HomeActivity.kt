package com.szlachta.medialibrary.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.model.ItemTypeEnum
import com.szlachta.medialibrary.ui.SignInActivity
import com.szlachta.medialibrary.ui.form.FormActivity
import com.szlachta.medialibrary.ui.form.FormModeEnum
import com.szlachta.medialibrary.ui.profile.ProfileActivity
import com.szlachta.medialibrary.ui.search.SearchActivity
import com.szlachta.medialibrary.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    private val actionBarOnClickListener = View.OnClickListener {
        val intent: Intent = Intent(this, SearchActivity::class.java)
            .putExtra(ItemTypeEnum.TAG, getItemType())
        startActivity(intent)
    }

    private val floatingActionButtonOnClickListener = View.OnClickListener {
        val intent = Intent(this, FormActivity::class.java)
            .putExtra(FormModeEnum.TAG, FormModeEnum.CREATE)
            // TODO: pass item status to the form (to select the right chip)
            .putExtra(ItemTypeEnum.TAG, getItemType())
        startActivity(intent)
    }

    private val navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            if (bottom_navigation.selectedItemId == it.itemId) {
                false
            } else {
                val position = when (it.itemId) {
                    R.id.action_games -> ItemTypeEnum.GAMES.position
                    R.id.action_movies -> ItemTypeEnum.MOVIES.position
                    R.id.action_books -> ItemTypeEnum.BOOKS.position
                    else -> ItemTypeEnum.GAMES.position
                }
                pager_home.setCurrentItem(position, false)
                setActionBarTitle()
                true
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_home)

        setSupportActionBar(action_bar_home)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        pager_home.adapter = BottomNavigationPagerAdapter(this)
        pager_home.isUserInputEnabled = false

        action_bar_home.setOnClickListener(actionBarOnClickListener)
        floating_action_button_add.setOnClickListener(floatingActionButtonOnClickListener)
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }

    // fixes a bug; TODO: fix the bug other way
    override fun onResume() {
        super.onResume()
        setActionBarTitle()
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

    private fun signOut() {
        val intent = SignInActivity.getLaunchIntent(this)
        startActivity(intent)
        viewModel.signOut()
        finish()
    }

    private fun getItemType(): ItemTypeEnum = ItemTypeEnum.getByPosition(pager_home.currentItem)

    private fun setActionBarTitle() {
        action_bar_home_title.text = when (pager_home.currentItem) {
            ItemTypeEnum.GAMES.position -> getString(R.string.search_games)
            ItemTypeEnum.MOVIES.position -> getString(R.string.search_movies)
            ItemTypeEnum.BOOKS.position -> getString(R.string.search_books)
            else -> ""
        }
    }
}
