package com.szlachta.medialibrary.ui.profile

import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.model.User
import com.szlachta.medialibrary.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    companion object {
        const val RC_SIGN_OUT = 1
    }

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_profile)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val user: User = viewModel.getUserData()

        Glide.with(this)
            .load(user.photoUrl)
            .into(findViewById(R.id.profile_image))
        profile_text.text = user.displayName
        profile_email.text = user.email

        button_sign_out.setOnClickListener {
            setResult(RC_SIGN_OUT)
            finish()
        }
    }
}
