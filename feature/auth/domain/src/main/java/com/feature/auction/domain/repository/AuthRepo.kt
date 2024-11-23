package com.feature.auction.domain.repository

import com.core.common.model.LogoutDTO
import com.core.common.model.RefreshDTO
import com.feature.auction.domain.models.ConfirmResetRequest
import com.feature.auction.domain.models.ConfirmResetResponse
import com.feature.auction.domain.models.LegalRequest
import com.feature.auction.domain.models.LogoutRequest
import com.feature.auction.domain.models.LogoutResponse
import com.feature.auction.domain.models.PassResetRequest
import com.feature.auction.domain.models.ResendOtp
import com.feature.auction.domain.models.SignInRequest
import com.feature.auction.domain.models.SignInResponse
import com.feature.auction.domain.models.SignUpRequest
import com.feature.auction.domain.models.SignUpResponse
import com.feature.auction.domain.models.SocialSignInRequest
import com.feature.auction.domain.models.SocialsRequest
import com.feature.auction.domain.models.SocialsResponse
import com.feature.auction.domain.models.VerifyRequest
import com.feature.auction.domain.models.VerifyResponse
import retrofit2.Response

interface AuthRepo {
    suspend fun signupGoogle(
        requestBody: SocialsRequest
    ): SocialsResponse

    suspend fun signupFacebook(
        requestBody: SocialsRequest
    ): SocialsResponse

    suspend fun signInGoogle(requestBody: SocialSignInRequest): SocialsResponse

    suspend fun signInFacebook(requestBody: SocialSignInRequest): SocialsResponse

    suspend fun signupIndividual(signUpRequest: SignUpRequest): Result<SignUpResponse>

    suspend fun signupLegal(signUpRequest: LegalRequest): Result<SignUpResponse>

    suspend fun signIn(signInRequest: SignInRequest): Result<SignInResponse>

    suspend fun logout(logoutRequest: LogoutRequest): LogoutResponse

    suspend fun verify(
        reset: Boolean,
        uuid: String?,
        verifyRequest: VerifyRequest
    ): Result<VerifyResponse>

    suspend fun resendOtp(uuid: String?, reset: Boolean): ResendOtp

    suspend fun refresh(): Response<RefreshDTO>

    suspend fun passReset(passResetRequest: PassResetRequest): Result<ResendOtp>

    suspend fun confirmReset(
        token: String?,
        uidb: String?,
        confirmResetRequest: ConfirmResetRequest
    ): Result<ConfirmResetResponse>
}


