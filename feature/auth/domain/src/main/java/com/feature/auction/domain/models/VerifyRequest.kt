package com.feature.auction.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VerifyRequest(
    @Json(name = "otp_code")
    val otpCode: String?
)
