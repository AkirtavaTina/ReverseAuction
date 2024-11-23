package com.feature.auction.data.repository

import com.core.common.ErrorMapper
import com.core.common.callAndMap
import com.core.common.mapError
import com.core.common.model.LogoutDTO
import com.core.common.model.RefreshDTO
import com.feature.auction.data.AuthService
import com.feature.auction.data.mapper.AuthMapper
import com.feature.auction.domain.models.ConfirmResetRequest
import com.feature.auction.domain.models.ConfirmResetResponse
import com.feature.auction.domain.models.SocialsRequest
import com.feature.auction.domain.models.SocialsResponse
import com.feature.auction.domain.models.SocialSignInRequest
import com.feature.auction.domain.models.LegalRequest
import com.feature.auction.domain.models.LogoutRequest
import com.feature.auction.domain.models.LogoutResponse
import com.feature.auction.domain.models.PassResetRequest
import com.feature.auction.domain.models.ResendOtp
import com.feature.auction.domain.models.SignInRequest
import com.feature.auction.domain.models.SignInResponse
import com.feature.auction.domain.models.SignUpRequest
import com.feature.auction.domain.models.SignUpResponse
import com.feature.auction.domain.models.VerifyRequest
import com.feature.auction.domain.models.VerifyResponse
import com.feature.auction.domain.repository.AuthRepo
import retrofit2.Response
import javax.inject.Inject


class AuthRepoImpl @Inject constructor(
    private val authService: AuthService, private val authMapper: AuthMapper,
    private val errorMapper: ErrorMapper,
) : AuthRepo {

    override suspend fun signupGoogle(
        requestBody: SocialsRequest
    ): SocialsResponse = authMapper.socialSignUpMapper(authService.signupGoogle(requestBody))

    override suspend fun signInGoogle(requestBody: SocialSignInRequest): SocialsResponse =
        authMapper.socialSignInMapper(authService.signInGoogle(requestBody))

    override suspend fun signupFacebook(requestBody: SocialsRequest): SocialsResponse =
        authMapper.socialSignUpMapper(authService.signupFacebook(requestBody))

    override suspend fun signInFacebook(requestBody: SocialSignInRequest): SocialsResponse =
        authMapper.socialSignInMapper(authService.signInFacebook(requestBody))

    override suspend fun signupIndividual(signUpRequest: SignUpRequest): Result<SignUpResponse> =
        callAndMap(
            serviceCall = {
                authService.signupIndividual(signUpRequest)
            },
            mapper = { response ->
                authMapper.signUpMapper(response)
            }
        ).mapError(errorMapper::invoke)


    override suspend fun signupLegal(signUpRequest: LegalRequest): Result<SignUpResponse> =
        callAndMap(
            serviceCall = {
                authService.signupLegal(signUpRequest)
            },
            mapper = { response ->
                authMapper.signUpMapper(response)
            }
        ).mapError(errorMapper::invoke)


    override suspend fun signIn(signInRequest: SignInRequest): Result<SignInResponse> =
        callAndMap(
            serviceCall = {
                authService.login(signInRequest)
            },
            mapper = { response ->
                authMapper.loginMapper(response)
            }
        ).mapError(errorMapper::invoke)


    override suspend fun logout(logoutRequest: LogoutRequest): LogoutResponse =
        authMapper.logoutMapper(authService.logout(logoutRequest))

     override suspend fun verify(
        reset: Boolean,
        uuid: String?,
        verifyRequest: VerifyRequest
    ): Result<VerifyResponse> =
      callAndMap(
    serviceCall = {
                authService.verify(uuid, reset, verifyRequest)
            },
            mapper = { response ->
                authMapper.verifyMapper(response)
            }
      ).mapError(errorMapper::invoke)

    override suspend fun resendOtp(uuid: String?, reset: Boolean): ResendOtp =
        authMapper.resendMapper(authService.resendOtp(uuid, reset = reset))

    override suspend fun refresh(): Response<RefreshDTO> = authService.refresh()

    override suspend fun passReset(passResetRequest: PassResetRequest): Result<ResendOtp> =
        callAndMap(
            serviceCall = {
                authService.passReset(passResetRequest)
            },
            mapper = { response ->
                authMapper.resendMapper(response)
            }
        ).mapError(errorMapper::invoke)


    override suspend fun confirmReset(
        token: String?,
        uidb: String?,
        confirmResetRequest: ConfirmResetRequest
    ): Result<ConfirmResetResponse> =
        callAndMap(
            serviceCall = {
                authService.confirmReset(token, uidb, confirmResetRequest)
            },
            mapper = { response ->
                authMapper.confPassMapper(response)
            }
        ).mapError(errorMapper::invoke)


}