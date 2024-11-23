package com.android.example.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileInfo(
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "date_of_birth")
    val dateOfBirth: String?,
    @Json(name = "id_number")
    val idNumber: String?,
    val gender: String?,
    val user: User
)

data class User(
    val email: String?,
    @Json(name = "phone_number")
    val phoneNumber: String?,
    val country: String?,
    @Json(name = "mfa_is_enabled")
    val mfaIsEnabled: Boolean?,
    @Json(name = "mfa_method")
    val mfaMethod: String?
)
