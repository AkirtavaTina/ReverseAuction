package com.feature.auction.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SocialsResponse(
    val access: String,
    val refresh: String
)