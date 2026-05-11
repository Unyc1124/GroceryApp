package com.oceanx.freshbasket.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.oceanx.freshbasket.databinding.ActivityLoginBinding
import com.oceanx.freshbasket.ui.main.MainActivity
import com.oceanx.freshbasket.utils.SessionManager
import com.oceanx.freshbasket.utils.hide
import com.oceanx.freshbasket.utils.show

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val FAKE_OTP = "1234"
    private var currentPhone = ""
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPhoneScreen()
        setupOtpScreen()
    }

    private fun setupPhoneScreen() {
        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnSendOtp.isEnabled = s?.length == 10
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSendOtp.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            if (phone.length == 10) {
                currentPhone = phone
                showOtpScreen()
                startResendTimer()
            } else {
                binding.tilPhone.error = "Enter valid 10-digit number"
            }
        }
    }

    private fun setupOtpScreen() {
        // Auto-advance OTP fields
        val otpFields = listOf(binding.otp1, binding.otp2, binding.otp3, binding.otp4)
        otpFields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && index < otpFields.size - 1) {
                        otpFields[index + 1].requestFocus()
                    }
                    if (s?.length == 0 && index > 0) {
                        otpFields[index - 1].requestFocus()
                    }
                    checkOtpComplete(otpFields.map { it.text.toString() }.joinToString(""))
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }

        binding.btnVerify.setOnClickListener {
            val enteredOtp = otpFields.map { it.text.toString() }.joinToString("")
            verifyOtp(enteredOtp)
        }

        binding.tvResend.setOnClickListener {
            startResendTimer()
            binding.tvResend.isEnabled = false
        }

        binding.tvChangeNumber.setOnClickListener {
            timer?.cancel()
            showPhoneScreen()
        }
    }

    private fun checkOtpComplete(otp: String) {
        binding.btnVerify.isEnabled = otp.length == 4
    }

    private fun verifyOtp(otp: String) {
        if (otp == FAKE_OTP) {
            timer?.cancel()
            SessionManager.saveLogin(this, currentPhone)
            binding.tvOtpError.hide()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            binding.tvOtpError.show()
            binding.tvOtpError.text = "Invalid OTP. Use 1234"
            shakeOtpFields()
        }
    }

    private fun shakeOtpFields() {
        listOf(binding.otp1, binding.otp2, binding.otp3, binding.otp4).forEach { field ->
            field.text?.clear()
        }
        binding.otp1.requestFocus()
    }

    private fun startResendTimer() {
        timer?.cancel()
        binding.tvResend.isEnabled = false
        var seconds = 30
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                seconds = (millisUntilFinished / 1000).toInt()
                binding.tvResendTimer.text = "Resend in ${seconds}s"
                binding.tvResendTimer.show()
                binding.tvResend.hide()
            }
            override fun onFinish() {
                binding.tvResendTimer.hide()
                binding.tvResend.show()
                binding.tvResend.isEnabled = true
            }
        }.start()
    }

    private fun showOtpScreen() {
        hideKeyboard()
        binding.layoutPhone.hide()
        binding.layoutOtp.show()
        binding.tvOtpSubtitle.text = "Enter OTP sent to +91 $currentPhone\n(Hint: use 1234)"
    }

    private fun showPhoneScreen() {
        binding.layoutOtp.hide()
        binding.layoutPhone.show()
        binding.etPhone.text?.clear()
        binding.etPhone.requestFocus()
        listOf(binding.otp1, binding.otp2, binding.otp3, binding.otp4).forEach { it.text?.clear() }
        binding.tvOtpError.hide()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { imm.hideSoftInputFromWindow(it.windowToken, 0) }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
