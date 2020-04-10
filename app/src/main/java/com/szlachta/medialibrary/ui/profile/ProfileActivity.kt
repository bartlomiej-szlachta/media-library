package com.szlachta.medialibrary.ui.profile

import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.szlachta.medialibrary.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_profile)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

        Glide.with(this)
            .load(firebaseUser.photoUrl)
            .into(findViewById(R.id.profile_image))
        profile_text.text = firebaseUser.displayName
        profile_email.text = firebaseUser.email

        button_sign_out.setOnClickListener {
            // TODO: log out
        }
    }
}
