package com.szlachta.medialibrary.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.szlachta.medialibrary.ui.home.HomeActivity

class MainEmptyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loggedUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val intent: Intent = if (loggedUser == null) {
            SignInActivity.getLaunchIntent(this)
        } else {
            HomeActivity.getLaunchIntent(this)
        }

        Firebase.database.setPersistenceEnabled(true)

        startActivity(intent)
        finish()
    }
}
