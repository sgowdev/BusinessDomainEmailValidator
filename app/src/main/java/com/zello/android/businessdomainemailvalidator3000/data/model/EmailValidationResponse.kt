package com.zello.android.businessdomainemailvalidator3000.data.model

import com.google.gson.annotations.SerializedName

data class EmailValidationResponse(
    @SerializedName("is_business_domain")
    val isBusinessDomain: Boolean,
)
