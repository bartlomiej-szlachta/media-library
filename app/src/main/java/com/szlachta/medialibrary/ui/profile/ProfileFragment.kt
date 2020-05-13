package com.szlachta.medialibrary.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.model.User
import com.szlachta.medialibrary.ui.SignInActivity
import com.szlachta.medialibrary.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_profile.profile_email
import kotlinx.android.synthetic.main.fragment_profile.profile_image
import kotlinx.android.synthetic.main.fragment_profile.profile_text
import kotlinx.android.synthetic.main.fragment_profile.text_sign_out

class ProfileFragment : BottomSheetDialogFragment() {
    companion object {
        const val TAG_PROFILE = "tag-profile"
    }

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        val user: User = viewModel.getUserData()

        Glide.with(this).load(user.photoUrl).into(profile_image)
        profile_text.text = user.displayName
        profile_email.text = user.email

        text_sign_out.setOnClickListener {
            viewModel.signOut()
            dismiss()
            val intent = SignInActivity.getLaunchIntent(activity!!)
            startActivity(intent)
            activity!!.finish()
        }
    }
}
