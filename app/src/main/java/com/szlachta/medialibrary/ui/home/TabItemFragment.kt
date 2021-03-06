package com.szlachta.medialibrary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.model.Item
import com.szlachta.medialibrary.model.ItemTypeEnum
import com.szlachta.medialibrary.model.ItemsList
import com.szlachta.medialibrary.model.ItemStatusEnum
import com.szlachta.medialibrary.ui.list.ImageLoader
import com.szlachta.medialibrary.ui.itemoptions.BottomSheetFragment
import com.szlachta.medialibrary.ui.list.ListAdapter
import com.szlachta.medialibrary.ui.list.OnItemClickListener
import com.szlachta.medialibrary.viewmodel.DatabaseViewModel
import kotlinx.android.synthetic.main.fragment_tab_item.rv_items_list

class TabItemFragment : Fragment(), OnItemClickListener, ImageLoader {
    private lateinit var itemType: ItemTypeEnum
    private lateinit var itemStatus: ItemStatusEnum

    private val viewModel: DatabaseViewModel by lazy {
        ViewModelProvider(this).get(DatabaseViewModel::class.java)
    }

    private val dataObserver = Observer<ItemsList> {
        if (it.errorMessage != null) {
            onError(it.errorMessage.toString())
        } else {
            onSuccess(it.items!!)
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
            ?.takeIf { it.containsKey(ItemTypeEnum.TAG) }
            ?.apply {
                itemType = getSerializable(ItemTypeEnum.TAG) as ItemTypeEnum
            }
            ?.takeIf { it.containsKey(ItemStatusEnum.TAG) }
            ?.apply {
                itemStatus = getSerializable(ItemStatusEnum.TAG) as ItemStatusEnum
            }
        viewModel.getItemsList(itemType, itemStatus).observe(viewLifecycleOwner, dataObserver)
    }

    override fun onItemClicked(item: Item) {
        val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.arguments = Bundle().apply {
            putSerializable(BottomSheetFragment.TAG_ITEM, item)
        }
        bottomSheetFragment.show(activity!!.supportFragmentManager, BottomSheetFragment.TAG_SHEET)
    }

    override fun loadImage(url: String?, into: ImageView) {
        if (url != null) {
            Glide.with(activity!!).load(url).into(into)
        }
    }

    private fun onSuccess(items: List<Item>) {
        rv_items_list.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rv_items_list.adapter = ListAdapter(items, this, this)
    }

    private fun onError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}
