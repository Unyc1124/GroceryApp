package com.oceanx.freshbasket.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.oceanx.freshbasket.FreshBasketApp
import com.oceanx.freshbasket.R
import com.oceanx.freshbasket.databinding.ActivityMainBinding
import com.oceanx.freshbasket.utils.ThemeManager
import com.oceanx.freshbasket.viewmodel.CartViewModel
import com.oceanx.freshbasket.viewmodel.CartViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory((application as FreshBasketApp).database.cartDao().let {
            com.oceanx.freshbasket.data.repository.CartRepository(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager.applyStoredTheme(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        observeCartCount()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.cartFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNav.visibility = View.GONE
                }
            }
        }
    }

    private fun observeCartCount() {
        lifecycleScope.launch {
            cartViewModel.cartCount.collectLatest { count ->
                val badge = binding.bottomNav.getOrCreateBadge(R.id.cartFragment)
                if (count > 0) {
                    badge.isVisible = true
                    badge.number = count
                } else {
                    badge.isVisible = false
                }
            }
        }
    }
}
