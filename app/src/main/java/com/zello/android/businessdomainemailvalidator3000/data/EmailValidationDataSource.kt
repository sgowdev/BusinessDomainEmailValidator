package com.zello.android.businessdomainemailvalidator3000.data

import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.zello.android.businessdomainemailvalidator3000.data.model.EmailValidationResponse
import org.json.JSONObject

class EmailValidationDataSource {
    suspend fun submitEmailForValidation(email: String): Result<EmailValidationResponse> {
        val json = JSONObject()
        json.put("email", email)
        val (request, response, result) = Fuel.post(
            "https://zello.com/check_email.php"
        ).body(json.toString())
            .response()
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
