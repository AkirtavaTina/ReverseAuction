package com.feature.auction.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SocialSignInRequest(
    val token: String
)
