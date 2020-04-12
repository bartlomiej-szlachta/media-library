package com.szlachta.medialibrary.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.model.Item

class ListAdapter(
    private val items: List<Item>,
    private val itemClickListener: OnItemClickListener,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_search_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], itemClickListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemId: String? = null
        private val itemTitle: TextView = itemView.findViewById(R.id.text_item_title)
        private val itemYear: TextView = itemView.findViewById(R.id.text_item_year)
        private val itemImage: ImageView = itemView.findViewById(R.id.image_item)

        fun bind(item: Item, clickListener: OnItemClickListener) {
            itemId = item.id
            itemTitle.text = item.title
            itemYear.text = item.year.toString()
            imageLoader.loadImage(item.imageUrl, itemImage)
            itemView.setOnClickListener { clickListener.onItemClicked(item) }
        }
    }
}