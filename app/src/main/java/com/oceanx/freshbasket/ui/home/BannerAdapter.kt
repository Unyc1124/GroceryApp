package com.oceanx.freshbasket.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oceanx.freshbasket.databinding.ItemBannerBinding

data class BannerItem(
    val emoji: String,
    val title: String,
    val subtitle: String,
    val bgColor: String
)

class BannerAdapter : ListAdapter<BannerItem, BannerAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvBannerEmoji.text = item.emoji
            tvBannerTitle.text = item.title
            tvBannerSubtitle.text = item.subtitle
            root.setCardBackgroundColor(Color.parseColor(item.bgColor))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<BannerItem>() {
        override fun areItemsTheSame(oldItem: BannerItem, newItem: BannerItem) = oldItem.title == newItem.title
        override fun areContentsTheSame(oldItem: BannerItem, newItem: BannerItem) = oldItem == newItem
    }
}
