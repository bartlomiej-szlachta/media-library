package com.szlachta.medialibrary.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.szlachta.medialibrary.model.*
import java.util.stream.Collectors

class DatabaseRepository private constructor() {
    companion object {
        private var repository: DatabaseRepository? = null

        fun getInstance(): DatabaseRepository {
            if (repository == null) {
                repository = DatabaseRepository()
            }
            return repository!!
        }
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val database: DatabaseReference by lazy {
        Firebase.database.setPersistenceEnabled(true)
        Firebase.database.reference
            .child("users")
            .child(auth.currentUser!!.uid)
    }

    fun getItemsList(
        itemType: ItemTypeEnum,
        itemStatus: ItemStatusEnum
    ): MutableLiveData<ListResponse> {
        val items = MutableLiveData<ListResponse>()

        database.child(itemType.name)
            .orderByChild("status")
            .equalTo(itemStatus.name)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    @Suppress("UNCHECKED_CAST") val data: Map<String, Map<String, Any>> =
                        p0.value as Map<String, Map<String, Any>>? ?: emptyMap()

                    val list = data.entries.stream().map {
                        Item(
                            title = it.value["title"] as String,
                            firebaseId = it.key,
                            remoteId = it.value["remoteId"] as String?,
                            year = it.value["year"]?.toString()?.toInt(),
                            imageUrl = it.value["imageUrl"] as String?
                        )
                    }.collect(Collectors.toList())

                    items.value = ListResponse(items = list)
                }

                override fun onCancelled(p0: DatabaseError) {
                    items.value = ListResponse(errorMessage = p0.message)
                }
            })

        return items
    }

    fun saveItem(item: Item, itemType: ItemTypeEnum): MutableLiveData<BasicResponse> {
        val key = item.firebaseId ?: database.child(itemType.name).push().key!!
        val result = MutableLiveData<BasicResponse>()
        database.child(itemType.name).child(key).setValue(item)
            .addOnCompleteListener {
                result.value = BasicResponse(success = true)
            }
            .addOnCanceledListener {
                result.value = BasicResponse(success = false)
            }
        return result
    }
}