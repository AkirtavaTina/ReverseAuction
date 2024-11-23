package com.feature.auction.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResendOtp(
    val detail: String?,
    @Json(name = "user_id")
    val userId: String?
)