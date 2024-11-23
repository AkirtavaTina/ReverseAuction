package com.android.example.domain.repository

import com.android.example.domain.model.ProfileInfo

interface ProfileRepository {
    suspend fun getUserProfile(accessToken: String): ProfileInfo?
    suspend fun deleteUser(accessToken: String): Boolean
    suspend fun checkPassword(password: String, accessToken: String): Boolean
    suspend fun changePassword(accessToken: String, oldPassword: String, newPassword1: String, newPassword2: String): Boolean
}