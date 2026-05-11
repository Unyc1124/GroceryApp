package com.oceanx.freshbasket.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oceanx.freshbasket.R
import com.oceanx.freshbasket.databinding.FragmentHomeBinding
import com.oceanx.freshbasket.ui.main.MainActivity
import com.oceanx.freshbasket.utils.SessionManager
import com.oceanx.freshbasket.utils.ThemeManager
import com.oceanx.freshbasket.utils.hide
import com.oceanx.freshbasket.utils.show
import com.oceanx.freshbasket.viewmodel.CartViewModel
import com.oceanx.freshbasket.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private val cartViewModel: CartViewModel by activityViewModels {
        (requireActivity() as MainActivity).cartViewModel.let {
            object : androidx.lifecycle.ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return it as T
                }
            }
        }
    }

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter
    private lateinit var bannerAdapter: BannerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupAdapters()
        observeData()
        setupSearch()
        setupThemeToggle()
    }

    private fun setupUI() {
        val phone = SessionManager.getPhone(requireContext())
        binding.tvGreeting.text = "Hey! 👋"
        binding.tvPhone.text = "+91 $phone"
        binding.tvDeliveryTime.text = "Delivering in 10 mins 🚀"
    }

    private fun setupAdapters() {
        // Banner
        bannerAdapter = BannerAdapter()
        binding.vpBanner.adapter = bannerAdapter
        binding.dotsIndicator.attachTo(binding.vpBanner)
        bannerAdapter.submitList(getBannerItems())

        // Categories
        categoryAdapter = CategoryAdapter { category ->
            homeViewModel.loadProducts(category.name)
        }
        binding.rvCategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = categoryAdapter

        // Products
        productAdapter = ProductAdapter(
            onAddToCart = { product ->
                val mainActivity = requireActivity() as MainActivity
                mainActivity.cartViewModel.addToCart(product)
                mainActivity.cartViewModel.addToCartEvent.observe(viewLifecycleOwner) { msg ->
                    binding.root.let {
                        com.google.android.material.snackbar.Snackbar.make(it, msg, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
                            .setAnchorView(requireActivity().findViewById(R.id.bottom_nav))
                            .show()
                    }
                }
            }
        )
        binding.rvProducts.layoutManager = GridLayoutManager(context, 2)
        binding.rvProducts.adapter = productAdapter
    }

    private fun observeData() {
        homeViewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }
        homeViewModel.products.observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
            binding.tvProductsCount.text = "${it.size} items"
        }
        homeViewModel.searchResults.observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
            binding.tvProductsCount.text = "${it.size} results"
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                homeViewModel.search(s.toString())
                if (s.isNullOrEmpty()) {
                    binding.ivClear.hide()
                } else {
                    binding.ivClear.show()
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.ivClear.setOnClickListener {
            binding.etSearch.text?.clear()
        }
    }

    private fun setupThemeToggle() {
        val isDark = ThemeManager.isDarkMode(requireContext())
        binding.switchTheme.isChecked = isDark
        updateThemeIcon(isDark)

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            ThemeManager.setDarkMode(requireContext(), isChecked)
            requireActivity().recreate()
        }
    }

    private fun updateThemeIcon(isDark: Boolean) {
        binding.ivThemeIcon.setImageResource(
            if (isDark) R.drawable.ic_sun else R.drawable.ic_moon
        )
    }

    private fun getBannerItems(): List<BannerItem> = listOf(
        BannerItem("🥗", "Fresh Veggies", "Up to 30% off", "#E8F5E9"),
        BannerItem("🍎", "Seasonal Fruits", "Free delivery above ₹500", "#FFF3E0"),
        BannerItem("🥛", "Dairy Essentials", "Farm fresh daily", "#E3F2FD")
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
