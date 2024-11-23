package com.feature.auction.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfirmResetRequest(
    @Json(name = "new_password1")
    val newPassword1: String?,
    @Json(name = "new_password2")
    val newPassword2: String?
)
