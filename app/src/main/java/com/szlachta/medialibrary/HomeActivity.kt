package com.szlachta.medialibrary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val intent: Intent = Intent(this, SignInActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
        FirebaseAuth.getInstance().signOut()
    }
}
