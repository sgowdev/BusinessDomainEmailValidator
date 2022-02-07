package com.zello.android.businessdomainemailvalidator3000.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zello.android.businessdomainemailvalidator3000.data.EmailValidationDataSource
import com.zello.android.businessdomainemailvalidator3000.data.EmailValidationRepository

class EmailValidationViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmailValidationViewModel::class.java)) {
            return EmailValidationViewModel(
                emailValidationRepository = EmailValidationRepository(
                    dataSource = EmailValidationDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
