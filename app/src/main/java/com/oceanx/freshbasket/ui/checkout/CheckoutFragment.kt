package com.oceanx.freshbasket.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.oceanx.freshbasket.R
import com.oceanx.freshbasket.databinding.FragmentCheckoutBinding
import com.oceanx.freshbasket.ui.main.MainActivity
import com.oceanx.freshbasket.utils.formatPrice
import com.oceanx.freshbasket.viewmodel.CartViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by activityViewModels {
        object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return (requireActivity() as MainActivity).cartViewModel as T
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeTotals()
        setupPaymentOptions()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnPlaceOrder.setOnClickListener {
            validateAndPlaceOrder()
        }
    }

    private fun observeTotals() {
        lifecycleScope.launch {
            cartViewModel.cartTotal.collectLatest { total ->
                val subtotal = total ?: 0.0
                val delivery = cartViewModel.getDeliveryFee(subtotal)
                val grand = subtotal + delivery
                binding.tvSubtotal.text = subtotal.formatPrice()
                binding.tvDelivery.text = if (delivery == 0.0) "FREE" else delivery.formatPrice()
                binding.tvTotal.text = grand.formatPrice()
                binding.btnPlaceOrder.text = "Place Order • ${grand.formatPrice()}"
            }
        }
    }

    private fun setupPaymentOptions() {
        binding.rgPayment.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbCod -> binding.tvPaymentNote.text = "Pay cash on delivery"
                R.id.rbOnline -> binding.tvPaymentNote.text = "Pay securely online (UPI/Card)"
            }
        }
        binding.rbCod.isChecked = true
    }

    private fun validateAndPlaceOrder() {
        val address = binding.etAddress.text.toString().trim()
        val name = binding.etName.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()

        when {
            name.isEmpty() -> { binding.tilName.error = "Enter your name"; return }
            phone.length != 10 -> { binding.tilPhone.error = "Enter valid phone"; return }
            address.isEmpty() -> { binding.tilAddress.error = "Enter delivery address"; return }
            else -> {
                val paymentMethod = if (binding.rbCod.isChecked) "COD" else "Online"
                val orderId = "FB${System.currentTimeMillis().toString().takeLast(8)}"
                val bundle = Bundle().apply {
                    putString("orderId", orderId)
                    putString("paymentMethod", paymentMethod)
                    putString("address", address)
                }
                cartViewModel.clearCart()
                findNavController().navigate(R.id.action_checkoutFragment_to_orderSuccessFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
