package com.zello.android.businessdomainemailvalidator3000.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zello.android.businessdomainemailvalidator3000.R
import com.zello.android.businessdomainemailvalidator3000.data.EmailValidationRepository
import com.zello.android.businessdomainemailvalidator3000.data.Result
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EmailValidationViewModel(private val emailValidationRepository: EmailValidationRepository) :
    ViewModel() {
    private val _emailValidationForm = MutableLiveData<EmailValidationFormState>()
    val emailValidationFormState: LiveData<EmailValidationFormState> = _emailValidationForm

    private val _emailValidationResult = MutableLiveData<EmailValidationResult>()
    val emailValidationResult: LiveData<EmailValidationResult> = _emailValidationResult

    fun submitEmailForValidation(email: String) {
        GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT) {
            val result = emailValidationRepository.submitEmailForValidation(email)

            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                if (result is Result.Success) {
                    _emailValidationResult.value =
                        EmailValidationResult(success = EmailValidationView(isBusinessDomain = result.data.isBusinessDomain))
                } else {
                    _emailValidationResult.value = EmailValidationResult(error = R.string.email_invalid)
                }
            }
        }
    }

    fun onEmailChanged(email: String) {
        if (!isEmailValid(email)) {
            _emailValidationForm.value =
                EmailValidationFormState(emailError = R.string.invalid_email)
        } else {
            _emailValidationForm.value = EmailValidationFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
