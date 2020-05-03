package com.szlachta.medialibrary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.ui.ItemStatusEnum
import com.szlachta.medialibrary.ui.ItemTypeEnum
import kotlinx.android.synthetic.main.fragment_tab_item.text_view

class TabItemFragment(
    private val itemType: ItemTypeEnum,
    private val itemStatus: ItemStatusEnum
) : Fragment() {

    private val valueEventListener = object : ValueEventListener {
        override fun onDataChange(p0: DataSnapshot) {
            text_view.text = p0.value.toString()
        }

        override fun onCancelled(p0: DatabaseError) {
            Toast.makeText(activity, p0.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Firebase.database.reference.child(itemType.key).addValueEventListener(valueEventListener)
    }
}
