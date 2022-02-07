package com.zello.android.businessdomainemailvalidator3000.data

import com.zello.android.businessdomainemailvalidator3000.data.model.EmailValidationResponse

class EmailValidationRepository(val dataSource: EmailValidationDataSource) {
    suspend fun submitEmailForValidation(email: String): Result<EmailValidationResponse> {
        val result = dataSource.submitEmailForValidation(email)

        if (result is Result.Success) {
            emailValidationResponse = result.data
        }

        return result
    }

    private var emailValidationResponse: EmailValidationResponse? = null
}
