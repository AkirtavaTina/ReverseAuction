package com.android.example.domain.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "category_name")
    val categoryName: String?,
    @Json(name = "parent_category_id")
    val parentCategoryId: Int?,
    val id: Int?,
    @Json(name = "category_icon")
    val categoryIcon: String?
)
