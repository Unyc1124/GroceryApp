package com.oceanx.freshbasket

import android.app.Application
import com.oceanx.freshbasket.data.local.AppDatabase

class FreshBasketApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
}
