package com.feature.auction.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LegalRequest(
    val email: String?,
    val password: String?,
    @Json(name = "user_type")
    val userType: String?,
    val country: String?,
    @Json(name = "legal_profile")
    val legalProfile: LegalProfile?
) {
    data class LegalProfile(
        @Json(name = "company_name")
        val companyName: String?,
        @Json(name = "company_code")
        val companyCode: String?
    )
}
