package com.szlachta.medialibrary.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.ui.home.HomeActivity
import com.szlachta.medialibrary.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    companion object {
        private const val RC_SIGN_IN: Int = 1

        fun getLaunchIntent(from: Context) = Intent(from, SignInActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_sign_in)

        initializeGoogleSignInClient()

        button_sign_in.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                onRejected()
            }
        }
    }

    private fun initializeGoogleSignInClient() {
        val googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        googleSignInClient.revokeAccess()
    }

    private fun firebaseAuthWithGoogle(googleSignInAccount: GoogleSignInAccount) {
        val credential: AuthCredential = GoogleAuthProvider
            .getCredential(googleSignInAccount.idToken, null)
        viewModel.signInWithGoogle(credential).observe(this, Observer {
            if (it.success) {
                onSignedIn()
            } else {
                onRejected()
            }
        })
    }

    private fun onSignedIn() {
        val intent = HomeActivity.getLaunchIntent(this)
        startActivity(intent)
        finish()
    }

    private fun onRejected() {
        Toast.makeText(this, "Google sign in failed", Toast.LENGTH_LONG).show()
    }
}
