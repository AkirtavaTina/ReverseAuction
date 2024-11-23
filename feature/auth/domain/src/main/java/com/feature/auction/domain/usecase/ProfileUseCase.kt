package com.feature.auction.domain.usecase

import com.feature.auction.domain.models.LogoutRequest
import com.feature.auction.domain.models.LogoutResponse
import com.feature.auction.domain.repository.AuthRepo
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repoImpl: AuthRepo) {
    suspend fun logout(logoutRequest: LogoutRequest): LogoutResponse {
        return repoImpl.logout(logoutRequest)
    }
}