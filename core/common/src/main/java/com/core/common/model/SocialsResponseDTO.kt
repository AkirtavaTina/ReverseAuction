package com.core.common.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SocialsResponseDTO(
    val access: String,
    val refresh: String
)