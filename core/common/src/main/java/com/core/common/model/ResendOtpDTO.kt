package com.core.common.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResendOtpDTO(
    val detail: String?,
    @Json(name = "user_id")
    val userId: String?,
)
