package com.feature.auction.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SocialsRequest(
    val token: String,
    @Json(name = "user_type")
    val userType: String
)