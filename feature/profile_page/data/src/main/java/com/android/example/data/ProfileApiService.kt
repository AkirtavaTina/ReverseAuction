package com.android.example.data

import com.android.example.domain.model.ProfileInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ProfileApiService {
    @GET("/accounts/api/profile/individual/")
    suspend fun getUserProfile(
        @Header("Authorization") token: String // This will include "Bearer <access_token>"
    ): Response<ProfileInfo>

    @DELETE("/accounts/api/profile/delete/")
    suspend fun deleteUser(
        @Header("Authorization") token: String
    ): Response<Unit>

    @POST("/accounts/api/check/user/password/")
    suspend fun checkPassword(
        @Header("Authorization") token: String,
        @Body request: PasswordCheckRequest
    ): Response<Unit>

    @POST("/accounts/api/password/change/")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body request: PasswordChangeRequest
    ): Response<Unit>
}

data class PasswordCheckRequest(val password: String)

data class PasswordChangeRequest(
    val old_password: String,
    val new_password1: String,
    val new_password2: String
)
