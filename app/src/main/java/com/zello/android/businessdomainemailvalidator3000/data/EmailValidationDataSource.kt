package com.zello.android.businessdomainemailvalidator3000.data

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.awaitResponse
import com.github.kittinunf.fuel.core.awaitResult
import com.google.gson.Gson
import com.zello.android.businessdomainemailvalidator3000.data.model.EmailValidationResponse

class EmailValidationDataSource {
    suspend fun submitEmailForValidation(email: String): Result<EmailValidationResponse> {
        val (request, response, result) = Fuel.post(
            "https://zello.com/check_email.php",
            listOf("email" to email)
        ).response()
        val (bytes, error) = result
        return if (error == null) {
            val json = String(bytes!!)
            val response: EmailValidationResponse =
                Gson().fromJson(json, EmailValidationResponse::class.java)
            Result.Success(response)
        } else {
            Result.Error(error)
        }
    }
}

object MyDeserializer : ResponseDeserializable<EmailValidationResponse> {
    override fun deserialize(content: String): EmailValidationResponse =
        Gson().fromJson(content, EmailValidationResponse::class.java)
}
