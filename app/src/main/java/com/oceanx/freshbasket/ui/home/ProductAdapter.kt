package com.oceanx.freshbasket.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oceanx.freshbasket.data.model.Product
import com.oceanx.freshbasket.databinding.ItemProductBinding
import com.oceanx.freshbasket.utils.animateScale
import com.oceanx.freshbasket.utils.formatPrice
import com.oceanx.freshbasket.utils.hide
import com.oceanx.freshbasket.utils.show

class ProductAdapter(
    private val onAddToCart: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        with(holder.binding) {
            tvProductName.text = product.name
            tvPrice.text = product.price.formatPrice()
            tvOriginalPrice.text = product.originalPrice.formatPrice()
            tvOriginalPrice.paintFlags = tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvUnit.text = product.unit
            tvRating.text = product.rating.toString()
            ivProduct.setImageResource(product.imageRes)

            if (product.discount > 0) {
                tvDiscount.show()
                tvDiscount.text = "${product.discount}% OFF"
            } else {
                tvDiscount.hide()
            }

            if (product.isPopular) {
                tvPopular.show()
            } else {
                tvPopular.hide()
            }

            btnAddToCart.setOnClickListener {
                it.animateScale()
                onAddToCart(product)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }
}
