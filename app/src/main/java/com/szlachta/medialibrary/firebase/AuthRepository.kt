package com.szlachta.medialibrary.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.szlachta.medialibrary.model.BasicResponse
import com.szlachta.medialibrary.model.User

class AuthRepository private constructor() {
    companion object {
        private var repository: AuthRepository? = null

        fun getInstance(): AuthRepository {
            if (repository == null) {
                repository = AuthRepository()
            }
            return repository!!
        }
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun signInWithGoogle(credential: AuthCredential): MutableLiveData<BasicResponse> {
        val result = MutableLiveData<BasicResponse>()
        auth.signInWithCredential(credential).addOnCompleteListener {
            result.value = BasicResponse(success = it.isSuccessful)
        }
        return result
    }

    fun getUserInfo(): User {
        val firebaseUser = auth.currentUser!!
        return User(
            firebaseUser.uid,
            firebaseUser.displayName!!,
            firebaseUser.email!!,
            firebaseUser.photoUrl!!
        )
    }

    fun isSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        auth.signOut()
    }
}