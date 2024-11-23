package com.feature.auction.domain.usecase


import com.feature.auction.domain.models.ConfirmResetRequest
import com.feature.auction.domain.models.ConfirmResetResponse
import com.feature.auction.domain.models.PassResetRequest
import com.feature.auction.domain.models.ResendOtp
import com.feature.auction.domain.repository.AuthRepo
import javax.inject.Inject

class PassResetUseCase @Inject constructor(private val repoImpl: AuthRepo) {
    suspend operator fun invoke(passResetRequest: PassResetRequest): Result<ResendOtp> {
        return repoImpl.passReset(passResetRequest)
    }

    suspend operator fun invoke(
        token: String?,
        uidb: String?,
        confirmResetRequest: ConfirmResetRequest
    ): Result<ConfirmResetResponse> {
        return repoImpl.confirmReset(token, uidb, confirmResetRequest)
    }

}