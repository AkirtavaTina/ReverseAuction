package com.android.example.domain.usecase

import com.android.example.domain.model.ProfileInfo
import com.android.example.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun getUserProfile(accessToken: String): ProfileInfo? {
        return profileRepository.getUserProfile(accessToken)
    }

    suspend fun deleteUser(accessToken: String): Boolean {
        return profileRepository.deleteUser(accessToken)
    }

    suspend fun checkPassword(password:String, accessToken: String): Boolean {
        return profileRepository.checkPassword(password, accessToken)
    }

    suspend fun changePassword(password:String, password1:String, password2:String, accessToken: String): Boolean {
        return profileRepository.changePassword(accessToken = accessToken, oldPassword = password, newPassword1 = password1, newPassword2 = password2)
    }
}