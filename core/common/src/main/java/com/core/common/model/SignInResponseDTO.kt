package com.core.common.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInResponseDTO(
    val access: String?,
    val refresh: String?,
    @Json(name = "mfa_required")
    val mfaRequired: String?,
    @Json(name = "user_id")
    val userId: String?,
    val detail: String?
)
