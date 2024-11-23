package com.core.common.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshDTO(
    val refresh: String?,
    val access: String?,
    val message: String?,
    val details: Details,
    val description: String?,
    val code: Int?
)

data class Details(
    val refresh: List<String?>?
)

