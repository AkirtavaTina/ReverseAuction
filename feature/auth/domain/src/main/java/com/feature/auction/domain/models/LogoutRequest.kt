package com.feature.auction.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogoutRequest(
    val refresh: String?
)
