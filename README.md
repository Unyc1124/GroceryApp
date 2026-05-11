# FreshBasket — Mini Grocery Delivery App
### OceanX Agency — Kotlin Android Assignment

---

## App Overview

**FreshBasket** is a Blinkit-style grocery delivery app built with **Kotlin**, **MVVM architecture**, **Room DB**, and **Material Design 3**. It features a minimal yet impressive UI with full light/dark mode support.

---

## Features Implemented

| Feature | Status |
|---|---|
| Login with OTP (Fake OTP: `1234`) | ✅ |
| Home Screen — Search, Categories, Products | ✅ |
| Cart Screen — Add/Remove/Qty control | ✅ |
| Checkout — Address + Payment (COD / Online) | ✅ |
| Order Success — ID, Time, Summary | ✅ |
| Light / Dark Mode Toggle | ✅ |
| MVVM Architecture | ✅ |
| ViewModel + LiveData + StateFlow | ✅ |
| Room DB for cart persistence | ✅ |
| RecyclerView everywhere | ✅ |
| Search + Category filter | ✅ |
| Smooth animations | ✅ |
| Clean code structure | ✅ |

---

## Tech Stack

- **Language:** Kotlin 100%
- **Architecture:** MVVM (Model-View-ViewModel)
- **UI:** XML Layouts + Material Components 3
- **Navigation:** Jetpack Navigation Component
- **Local DB:** Room (for cart persistence)
- **Reactive:** StateFlow + LiveData + Coroutines
- **Animations:** Lottie (Order Success), custom anim XMLs
- **Image Loading:** Glide
- **Shimmer:** Facebook Shimmer (loading states)
- **Min SDK:** 24 (Android 7.0) | Target SDK: 34

---

## Project Structure

```
app/src/main/java/com/oceanx/freshbasket/
│
├── data/
│   ├── local/
│   │   ├── dao/          CartDao.kt
│   │   ├── entity/       CartItemEntity.kt
│   │   └── AppDatabase.kt
│   ├── model/            Product.kt, Category.kt
│   └── repository/       CartRepository.kt, ProductRepository.kt
│
├── ui/
│   ├── splash/           SplashActivity.kt
│   ├── login/            LoginActivity.kt
│   ├── main/             MainActivity.kt (BottomNav host)
│   ├── home/             HomeFragment.kt, ProductAdapter.kt,
│   │                     CategoryAdapter.kt, BannerAdapter.kt
│   ├── cart/             CartFragment.kt, CartAdapter.kt
│   ├── checkout/         CheckoutFragment.kt
│   └── success/          OrderSuccessFragment.kt
│
├── viewmodel/            CartViewModel.kt, HomeViewModel.kt
├── utils/                Extensions.kt, SessionManager.kt,
│                         ThemeManager.kt
└── FreshBasketApp.kt
```

---

## Local Project Setup Steps

### Prerequisites
- **Android Studio:** Hedgehog (2023.1.1) or newer
- **JDK:** 17
- **Android SDK:** API 34

### Step 1 — Open Project
```
File → Open → Select the "FreshBasket" folder
```

### Step 2 — Sync Gradle
Android Studio will auto-sync. If not:
```
File → Sync Project with Gradle Files
```
Or click the 🐘 Gradle sync button in the toolbar.

### Step 3 — Run the App
- Connect a physical device **or** create an AVD (API 24+)
- Press **▶ Run** (`Shift+F10`)
- The app will build and launch

### Step 4 — Login
- Enter any 10-digit mobile number
- Use OTP: **`1234`**


---

## UI Highlights

- **Splash Screen** — Animated green splash with basket icon
- **Login** — Curved header, OTP auto-advance fields, countdown timer
- **Home** — Banner carousel, horizontal category chips, 2-column product grid
- **Cart** — Live bill summary, free delivery progress banner
- **Checkout** — Clean form with payment radio options
- **Success** — Lottie check animation + order details card

---

## Dark Mode

Toggle the switch in the top-right of the Home screen. Theme persists across sessions via `SharedPreferences`.

---

## Data Persistence

- **Cart** — Stored in **Room DB** (`freshbasket_db`), persists across sessions
- **Login** — Stored in `SharedPreferences` via `SessionManager`
- **Theme** — Stored in `SharedPreferences` via `ThemeManager`

---


## For assignment i.e., temporary basis 

- All product data is **local/fake** (no backend needed)
- OTP is always `1234` (fake verification)
- Online payment is a UI placeholder (no payment gateway) for now later payments can be made active using razorpay or stripe.
- Free delivery on orders above ₹500

---

## 🎥 App Demo

<video src="video.mp4" controls width="400"></video>


