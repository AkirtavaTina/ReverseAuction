package com.feature.auction.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfirmResetResponse(
    val message: String?,
    val details: Any?,  // Can be either an object or a string
    val description: String?,
    val code: Int?
)

data class ErrorDetails(
    val uid: List<String>?,
    val token: List<String>?,
    @Json(name = "new_password1")
    val newPassword1: List<String>?,
    @Json(name = "new_password2")
    val newPassword2: List<String>?
)
