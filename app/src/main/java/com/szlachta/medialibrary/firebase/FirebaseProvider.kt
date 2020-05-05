package com.szlachta.medialibrary.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object FirebaseProvider {
    fun getDatabase(): DatabaseReference {
        return Firebase.database.reference
            .child("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
    }
}