package com.oceanx.freshbasket.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.oceanx.freshbasket.R
import com.oceanx.freshbasket.databinding.FragmentCartBinding
import com.oceanx.freshbasket.ui.main.MainActivity
import com.oceanx.freshbasket.utils.formatPrice
import com.oceanx.freshbasket.utils.hide
import com.oceanx.freshbasket.utils.show
import com.oceanx.freshbasket.viewmodel.CartViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by activityViewModels {
        object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return (requireActivity() as MainActivity).cartViewModel as T
            }
        }
    }

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observeCart()

        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
        }
    }

    private fun setupAdapter() {
        cartAdapter = CartAdapter(
            onIncrease = { cartViewModel.increaseQty(it) },
            onDecrease = { cartViewModel.decreaseQty(it) },
            onRemove = { cartViewModel.removeItem(it) }
        )
        binding.rvCart.layoutManager = LinearLayoutManager(context)
        binding.rvCart.adapter = cartAdapter
    }

    private fun observeCart() {
        lifecycleScope.launch {
            cartViewModel.cartItems.collectLatest { items ->
                cartAdapter.submitList(items)
                if (items.isEmpty()) {
                    binding.layoutEmpty.show()
                    binding.layoutCartContent.hide()
                } else {
                    binding.layoutEmpty.hide()
                    binding.layoutCartContent.show()
                }
            }
        }
        lifecycleScope.launch {
            cartViewModel.cartTotal.collectLatest { total ->
                val subtotal = total ?: 0.0
                val delivery = cartViewModel.getDeliveryFee(subtotal)
                val grand = subtotal + delivery

                binding.tvSubtotal.text = subtotal.formatPrice()
                binding.tvDelivery.text = if (delivery == 0.0) "FREE" else delivery.formatPrice()
                binding.tvGrandTotal.text = grand.formatPrice()
                binding.btnCheckout.text = "Checkout • ${grand.formatPrice()}"

                if (delivery == 0.0) {
                    binding.tvFreeDelivery.show()
                } else {
                    val needed = 500.0 - subtotal
                    binding.tvFreeDelivery.show()
                    binding.tvFreeDelivery.text = "Add ${needed.formatPrice()} more for free delivery!"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
