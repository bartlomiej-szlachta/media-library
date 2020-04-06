package com.szlachta.medialibrary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

        sign_out.setOnClickListener {
            signOut()
        }

        setDataOnView()
    }

    private fun setDataOnView() {
        val firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

        Glide.with(this)
            .load(firebaseUser.photoUrl)
            .into(findViewById(R.id.profile_image))
        profile_text.text = firebaseUser.displayName
        profile_email.text = firebaseUser.email
    }

    private fun signOut() {
        startActivity(SignInActivity.getLaunchIntent(this))
        FirebaseAuth.getInstance().signOut()
        finish()
    }
}
