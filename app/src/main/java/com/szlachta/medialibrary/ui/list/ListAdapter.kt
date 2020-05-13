package com.szlachta.medialibrary.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.model.Item
import com.szlachta.medialibrary.model.ItemTypeEnum

class ListAdapter(
    private val items: List<Item>,
    private val itemClickListener: OnItemClickListener,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], itemClickListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemRemoteId: String? = null
        private val itemTitle: TextView = itemView.findViewById(R.id.text_item_title)
        private val itemYear: TextView = itemView.findViewById(R.id.text_item_year)
        private val itemImage: ImageView = itemView.findViewById(R.id.image_item)

        fun bind(item: Item, clickListener: OnItemClickListener) {
            itemView.setOnClickListener { clickListener.onItemClicked(item) }
            itemRemoteId = item.remoteId
            itemTitle.text = item.title
            itemYear.text = item.year?.toString()
            if (item.imageUrl != null) {
                imageLoader.loadImage(item.imageUrl, itemImage)
            } else {
                val drawable = when (item.type!!) {
                    ItemTypeEnum.GAMES -> R.drawable.ic_games_white_50dp
                    ItemTypeEnum.MOVIES -> R.drawable.ic_local_movies_white_50dp
                    ItemTypeEnum.TV_SHOWS -> R.drawable.ic_live_tv_white_50dp
                    ItemTypeEnum.BOOKS -> R.drawable.ic_book_white_50dp
                }
                itemImage.setBackgroundResource(drawable)
            }
        }
    }
}