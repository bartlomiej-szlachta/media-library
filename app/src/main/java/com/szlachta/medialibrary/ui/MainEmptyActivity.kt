package com.szlachta.medialibrary.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.szlachta.medialibrary.ui.home.HomeActivity
import com.szlachta.medialibrary.viewmodel.AuthViewModel

class MainEmptyActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent: Intent = if (viewModel.isSignedIn()) {
            HomeActivity.getLaunchIntent(this)
        } else {
            SignInActivity.getLaunchIntent(this)
        }

        startActivity(intent)
        finish()
    }
}
