package com.oceanx.freshbasket.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oceanx.freshbasket.data.local.entity.CartItemEntity
import com.oceanx.freshbasket.databinding.ItemCartBinding
import com.oceanx.freshbasket.utils.animateScale
import com.oceanx.freshbasket.utils.formatPrice

class CartAdapter(
    private val onIncrease: (CartItemEntity) -> Unit,
    private val onDecrease: (CartItemEntity) -> Unit,
    private val onRemove: (CartItemEntity) -> Unit
) : ListAdapter<CartItemEntity, CartAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvItemName.text = item.name
            tvItemPrice.text = item.price.formatPrice()
            tvQuantity.text = item.quantity.toString()
            tvItemTotal.text = (item.price * item.quantity).formatPrice()
            ivProduct.setImageResource(item.imageRes)

            btnIncrease.setOnClickListener {
                it.animateScale()
                onIncrease(item)
            }
            btnDecrease.setOnClickListener {
                it.animateScale()
                onDecrease(item)
            }
            btnRemove.setOnClickListener {
                onRemove(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CartItemEntity>() {
        override fun areItemsTheSame(oldItem: CartItemEntity, newItem: CartItemEntity) = oldItem.productId == newItem.productId
        override fun areContentsTheSame(oldItem: CartItemEntity, newItem: CartItemEntity) = oldItem == newItem
    }
}
