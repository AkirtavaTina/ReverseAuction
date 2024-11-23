package com.feature.auction.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest(
    val email: String?,
    val password: String?,
    @Json(name = "user_type")
    val userType: String?,
    val country: String?,
    @Json(name = "individual_profile")
    val individualProfile: IndividualProfile?
) {
    data class IndividualProfile(
        @Json(name = "first_name")
        val firstName: String?,
        @Json(name = "last_name")
        val lastName: String?
    )
}
