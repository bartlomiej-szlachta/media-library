package com.szlachta.medialibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.szlachta.medialibrary.firebase.AuthRepository
import com.szlachta.medialibrary.model.BasicResponse
import com.szlachta.medialibrary.model.User

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository.getInstance()

    fun signInWithGoogle(credential: AuthCredential): LiveData<BasicResponse> {
        return repository.signInWithGoogle(credential)
    }

    fun getUserData(): User {
        return repository.getUserInfo()
    }

    fun isSignedIn(): Boolean {
        return repository.isSignedIn()
    }

    fun signOut() {
        repository.signOut()
    }
}