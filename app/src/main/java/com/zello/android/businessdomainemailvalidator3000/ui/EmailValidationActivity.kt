package com.zello.android.businessdomainemailvalidator3000.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zello.android.businessdomainemailvalidator3000.R
import com.zello.android.businessdomainemailvalidator3000.databinding.ActivityEmailValidationBinding
import com.zello.android.businessdomainemailvalidator3000.extensions.afterTextChanged
import com.zello.android.businessdomainemailvalidator3000.extensions.hideKeyboard

class EmailValidationActivity : AppCompatActivity() {
    private lateinit var emailValidationViewModel: EmailValidationViewModel
    private lateinit var binding: ActivityEmailValidationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailValidationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = binding.email
        val submit = binding.submit
        val loading = binding.loading

        emailValidationViewModel =
            ViewModelProvider(
                this,
                EmailValidationViewModelFactory()
            ).get(EmailValidationViewModel::class.java)

        emailValidationViewModel.emailValidationFormState.observe(
            this@EmailValidationActivity,
            Observer {
                val emailValidationFormState = it ?: return@Observer

                submit.isEnabled = emailValidationFormState.isDataValid

                if (emailValidationFormState.emailError != null) {
                    email.error = getString(emailValidationFormState.emailError)
                }
            })

        emailValidationViewModel.emailValidationResult.observe(
            this@EmailValidationActivity,
            Observer {
                val emailValidationResult = it ?: return@Observer

                loading.visibility = View.GONE

                if (emailValidationResult.error != null) {
                    showEmailValidationFailed(emailValidationResult.error)
                }
                if (emailValidationResult.success != null) {
                    updateUiWithResult(emailValidationResult.success)
                }
            })

        email.apply {
            afterTextChanged {
                emailValidationViewModel.onEmailChanged(
                    email.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        submitEmailForValidation(email.text.toString())
                }
                false
            }

            submit.setOnClickListener {
                submitEmailForValidation(email.text.toString())
            }
        }
    }

    private fun submitEmailForValidation(email: String) {
        hideKeyboard(binding.email)
        binding.loading.visibility = View.VISIBLE
        emailValidationViewModel.submitEmailForValidation(email)
    }

    private fun updateUiWithResult(model: EmailValidationView) {
        val message = getString(R.string.is_business_domain)
        val isBusinessDomain = model.isBusinessDomain
        Toast.makeText(
            applicationContext,
            "$message $isBusinessDomain",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showEmailValidationFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
