package com.feature.auction.domain.usecase

import com.feature.auction.domain.models.ResendOtp
import com.feature.auction.domain.models.SignInRequest
import com.feature.auction.domain.models.SignInResponse
import com.feature.auction.domain.models.VerifyRequest
import com.feature.auction.domain.models.VerifyResponse
import com.feature.auction.domain.repository.AuthRepo
import javax.inject.Inject

class VerificationUseCase @Inject constructor(private val repoImpl: AuthRepo) {
    suspend operator fun invoke( reset: Boolean,
                                 userId: String?,
                                 verifyRequest: VerifyRequest): Result<VerifyResponse> {
        return repoImpl.verify(
            reset,
            userId,
            verifyRequest = verifyRequest
        )
    }

    suspend fun resend(
        reset: Boolean,
        userId: String?
    ):ResendOtp{
       return repoImpl.resendOtp(
            userId, reset
        )
    }
}