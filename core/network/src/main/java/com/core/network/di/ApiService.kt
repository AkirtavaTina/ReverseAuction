package com.core.network.di

import com.core.common.model.RefreshDTO
import com.core.common.model.RefreshRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/accounts/api/refresh/")
    suspend fun refreshToken(
        @Body requestBody: RefreshRequest
    ): Response<RefreshDTO>

}