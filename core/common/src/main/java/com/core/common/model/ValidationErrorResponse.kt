package com.core.common.model

data class ValidationErrorResponse(
    val message: String?,
    val details: Map<String, List<String>>?,
    val description: String?,
    val code: Int?
)
