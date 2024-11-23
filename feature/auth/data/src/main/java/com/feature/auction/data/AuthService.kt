package com.feature.auction.data

import com.core.common.model.ConfirmResetResponseDTO
import com.core.common.model.LogoutDTO
import com.core.common.model.RefreshDTO
import com.core.common.model.ResendOtpDTO
import com.core.common.model.SignInResponseDTO
import com.core.common.model.SignUpResponseDTO
import com.core.common.model.SocialsResponseDTO
import com.core.common.model.VerifyDTO
import com.feature.auction.domain.models.ConfirmResetRequest
import com.feature.auction.domain.models.LegalRequest
import com.feature.auction.domain.models.LogoutRequest
import com.feature.auction.domain.models.PassResetRequest
import com.feature.auction.domain.models.SignInRequest
import com.feature.auction.domain.models.SignUpRequest
import com.feature.auction.domain.models.SocialSignInRequest
import com.feature.auction.domain.models.SocialsRequest
import com.feature.auction.domain.models.VerifyRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {
    @POST("/accounts/api/google/mobile/signup/")
    suspend fun signupGoogle(
        @Body requestBody: SocialsRequest
    ): SocialsResponseDTO

    @POST("/accounts/api/facebook/mobile/signup/")
    suspend fun signupFacebook(@Body requestBody: SocialsRequest): SocialsResponseDTO

    @POST("/accounts/api/google/mobile/signin/")
    suspend fun signInGoogle(@Body requestBody: SocialSignInRequest): SocialsResponseDTO

    @POST("/accounts/api/facebook/mobile/signin/")
    suspend fun signInFacebook(@Body requestBody: SocialSignInRequest): SocialsResponseDTO

    @POST("/accounts/api/signup/individual/")
    suspend fun signupIndividual(@Body requestBody: SignUpRequest): Response<SignUpResponseDTO>

    @POST("/accounts/api/signup/legal/")
    suspend fun signupLegal(@Body requestBody: LegalRequest): Response<SignUpResponseDTO>

    @POST("/accounts/api/login/")
    suspend fun login(
        @Body requestBody: SignInRequest
    ): Response<SignInResponseDTO>

    @POST("/accounts/api/logout/")
    suspend fun logout(@Body logoutRequest: LogoutRequest): LogoutDTO

    @POST("/accounts/api/email/verify/{uuid}/")
    suspend fun verify(
        @Path("uuid") uuid: String?,
        @Query("password-reset") reset: Boolean,
        @Body requestBody: VerifyRequest
    ): Response<VerifyDTO>

    @POST("/accounts/api/email/resend/{user_id}/otp/")
    suspend fun resendOtp(
        @Path("user_id") uuid: String?,
        @Query("password-reset") reset: Boolean
    ): ResendOtpDTO

    @POST("/accounts/api/refresh/")
    suspend fun refresh(): Response<RefreshDTO>

    @POST("/accounts/api/password/reset/")
    suspend fun passReset(@Body passResetRequest: PassResetRequest): Response<ResendOtpDTO>

    @POST("/accounts/api/password/reset/confirm/{uidb64}/{token}/")
    suspend fun confirmReset(
        @Path("token") token: String?,
        @Path("uidb64") uidb: String?,
        @Body confirmResetRequest: ConfirmResetRequest
    ): Response<ConfirmResetResponseDTO>
}
