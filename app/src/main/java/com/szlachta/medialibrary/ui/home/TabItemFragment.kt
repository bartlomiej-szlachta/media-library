package com.szlachta.medialibrary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.firebase.FirebaseProvider
import com.szlachta.medialibrary.model.Item
import com.szlachta.medialibrary.ui.ItemStatusEnum
import com.szlachta.medialibrary.ui.ItemTypeEnum
import com.szlachta.medialibrary.ui.list.ImageLoader
import com.szlachta.medialibrary.ui.list.ListAdapter
import com.szlachta.medialibrary.ui.list.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_tab_item.rv_items_list
import java.util.stream.Collectors

class TabItemFragment : Fragment() {
    private lateinit var itemType: ItemTypeEnum
    private lateinit var itemStatus: ItemStatusEnum
    private lateinit var database: DatabaseReference

    private val valueEventListener = object : ValueEventListener, OnItemClickListener, ImageLoader {
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

            rv_items_list.layoutManager =
                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            rv_items_list.adapter = ListAdapter(list, this, this)
        }

        override fun onCancelled(p0: DatabaseError) {
            Toast.makeText(activity, p0.message, Toast.LENGTH_SHORT).show()
        }

        override fun onItemClicked(item: Item) {
            Toast.makeText(activity, item.title, Toast.LENGTH_SHORT).show()
        }

        override fun loadImage(url: String?, into: ImageView) {
            if (url != null) {
                Glide.with(activity!!).load(url).into(into)
            }
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
        arguments
            ?.takeIf { it.containsKey(ItemTypeEnum.ARG) }
            ?.apply {
                itemType = getSerializable(ItemTypeEnum.ARG) as ItemTypeEnum
            }
            ?.takeIf { it.containsKey(ItemStatusEnum.ARG) }
            ?.apply {
                itemStatus = getSerializable(ItemStatusEnum.ARG) as ItemStatusEnum
            }
        database = FirebaseProvider.getDatabase().child(itemType.key)
        database.addValueEventListener(valueEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        database.removeEventListener(valueEventListener)
    }
}
