package com.feature.auction.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInResponse (
        val access: String?,
        val refresh: String?,
        val mfaRequired: String?,
        val userId: String?,
        val detail: String?
)



