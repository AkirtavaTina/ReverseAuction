package com.feature.auction.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogoutResponse(
    val refresh: String?,
    val detail: String?
)
