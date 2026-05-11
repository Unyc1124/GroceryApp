package com.oceanx.freshbasket.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.oceanx.freshbasket.ui.login.LoginActivity
import com.oceanx.freshbasket.ui.main.MainActivity
import com.oceanx.freshbasket.utils.SessionManager
import com.oceanx.freshbasket.utils.ThemeManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        ThemeManager.applyStoredTheme(this)

        splashScreen.setKeepOnScreenCondition { true }

        lifecycleScope.launch {
            delay(1800)
            splashScreen.setKeepOnScreenCondition { false }
            navigateNext()
        }
    }

    private fun navigateNext() {
        val intent = if (SessionManager.isLoggedIn(this)) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
