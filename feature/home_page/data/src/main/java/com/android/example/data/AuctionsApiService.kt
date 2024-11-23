package com.android.example.data

import com.android.example.domain.model.Category
import com.android.example.domain.model.Subcategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuctionsApiService {
    @GET("/auctions/api/category/all/")
    suspend fun getCategories(
        @Query("cursor") cursor: Int = 0,
        @Query("limit") limit: Int? = null
    ): Response<List<Category>>

    @GET("/auctions/api/category/super/")
    suspend fun getSuperCategories(): List<Category>

    @GET("/auctions/api/category/{category_id}/children/")
    suspend fun getSubCategories(
        @Path("category_id") categoryId: Int
    ): List<Subcategory>

}