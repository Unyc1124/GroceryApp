package com.oceanx.freshbasket.ui.success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.oceanx.freshbasket.R
import com.oceanx.freshbasket.databinding.FragmentOrderSuccessBinding
import java.util.concurrent.TimeUnit

class OrderSuccessFragment : Fragment() {

    private var _binding: FragmentOrderSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOrderSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = arguments?.getString("orderId") ?: "FB12345678"
        val paymentMethod = arguments?.getString("paymentMethod") ?: "COD"
        val address = arguments?.getString("address") ?: ""

        binding.tvOrderId.text = "#$orderId"
        binding.tvPaymentMethod.text = paymentMethod
        binding.tvDeliveryAddress.text = address
        binding.tvDeliveryTime.text = "Arriving in 10-15 minutes 🚀"

        // Play lottie animation
        binding.lottieSuccess.playAnimation()

        binding.btnContinueShopping.setOnClickListener {
            findNavController().navigate(R.id.action_orderSuccessFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
