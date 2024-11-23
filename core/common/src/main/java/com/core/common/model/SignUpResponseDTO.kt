package com.core.common.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpResponseDTO(
    @Json(name = "user_id")
    val userId: String?,
    val detail: String?,
    val email: List<String>?
)
