package com.feature.auction.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VerifyResponse(
    val detail: String?,
    val token: String?,
    val uidb64: String?,
    val access: String?,
    val refresh: String?,

)
