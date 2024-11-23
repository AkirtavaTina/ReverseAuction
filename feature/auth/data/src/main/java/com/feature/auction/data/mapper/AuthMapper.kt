package com.feature.auction.data.mapper

import com.core.common.model.ConfirmResetResponseDTO
import com.core.common.model.LogoutDTO
import com.core.common.model.SocialsResponseDTO
import com.core.common.model.ResendOtpDTO
import com.core.common.model.SignInResponseDTO
import com.core.common.model.SignUpResponseDTO
import com.core.common.model.VerifyDTO
import com.feature.auction.domain.models.ConfirmResetResponse
import com.feature.auction.domain.models.LogoutResponse
import com.feature.auction.domain.models.SocialsResponse
import com.feature.auction.domain.models.ResendOtp
import com.feature.auction.domain.models.SignInResponse
import com.feature.auction.domain.models.SignUpResponse
import com.feature.auction.domain.models.VerifyResponse
import javax.inject.Inject

class AuthMapper @Inject constructor(){
    fun loginMapper (loginResponse: SignInResponseDTO): SignInResponse {
        return SignInResponse(access = loginResponse.access,
            refresh = loginResponse.refresh,
            mfaRequired = loginResponse.mfaRequired,
            userId = loginResponse.userId,
            detail = loginResponse.detail)
    }

    fun logoutMapper(logoutResponse: LogoutDTO): LogoutResponse{
        return LogoutResponse(logoutResponse.refresh, logoutResponse.detail)
    }

    fun signUpMapper (signUpResponse: SignUpResponseDTO): SignUpResponse {
        return SignUpResponse(
            userId = signUpResponse.userId,
            email = signUpResponse.email,
            detail = signUpResponse.detail
        )
    }

    fun socialSignUpMapper (signUpResponse: SocialsResponseDTO): SocialsResponse {
        return SocialsResponse(
            access = signUpResponse.access,
            refresh = signUpResponse.refresh
        )
    }

    fun socialSignInMapper (signInResponse: SocialsResponseDTO): SocialsResponse {
        return SocialsResponse(
            access = signInResponse.access,
            refresh = signInResponse.refresh
        )
    }

    fun verifyMapper (verifyRequest: VerifyDTO): VerifyResponse {
        return VerifyResponse(
            detail = verifyRequest.detail,
            token = verifyRequest.token,
            uidb64 = verifyRequest.uidb64,
            access = verifyRequest.access,
            refresh = verifyRequest.refresh
        )
    }

    fun resendMapper (verifyRequest: ResendOtpDTO): ResendOtp {
        return ResendOtp(
            userId =  verifyRequest.userId,
            detail = verifyRequest.detail
        )
    }

    fun confPassMapper (confRequest: ConfirmResetResponseDTO): ConfirmResetResponse {
        return ConfirmResetResponse(
            message =  confRequest.message,
            description = confRequest.description,
            details = confRequest.details,
            code = confRequest.code
        )
    }

}