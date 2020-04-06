package com.szlachta.medialibrary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.szlachta.medialibrary.ui.HomeActivity
import com.szlachta.medialibrary.ui.SignInActivity

class MainEmptyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loggedUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val intent: Intent = if (loggedUser == null) {
            SignInActivity.getLaunchIntent(this)
        } else {
            HomeActivity.getLaunchIntent(this)
        }

        startActivity(intent)
        finish()
    }
}
