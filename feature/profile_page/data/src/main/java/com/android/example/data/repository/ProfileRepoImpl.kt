package com.android.example.data.repository

import com.android.example.data.PasswordChangeRequest
import com.android.example.data.PasswordCheckRequest
import com.android.example.data.ProfileApiService
import com.android.example.domain.model.ProfileInfo
import com.android.example.domain.repository.ProfileRepository
import javax.inject.Inject
import javax.inject.Named


class ProfileRepoImpl @Inject constructor(
    @Named("profileApi") private val profileApiService: ProfileApiService
) : ProfileRepository {

    override suspend fun getUserProfile(accessToken: String): ProfileInfo? {
        val response = profileApiService.getUserProfile("Bearer $accessToken")
        return if (response.isSuccessful) response.body() else null
    }

    override suspend fun deleteUser(accessToken: String): Boolean {
        val response = profileApiService.deleteUser("Bearer $accessToken")
        return response.isSuccessful
    }

    override suspend fun checkPassword(password: String, accessToken: String): Boolean {

        val response = profileApiService.checkPassword(
            "Bearer $accessToken",
            PasswordCheckRequest(password)
        )
        return response.isSuccessful
    }

    override suspend fun changePassword(accessToken: String, oldPassword: String, newPassword1: String, newPassword2: String): Boolean {

        val response = profileApiService.changePassword(
            "Bearer $accessToken",
            PasswordChangeRequest(oldPassword, newPassword1, newPassword2)
        )
        return response.isSuccessful
    }
}