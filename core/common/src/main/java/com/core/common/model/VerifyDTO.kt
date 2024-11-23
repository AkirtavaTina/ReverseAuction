package com.core.common.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VerifyDTO(
    val detail: String?,
    val token: String?,
    val uidb64: String?,
    val access: String?,
    val refresh: String?,
)
