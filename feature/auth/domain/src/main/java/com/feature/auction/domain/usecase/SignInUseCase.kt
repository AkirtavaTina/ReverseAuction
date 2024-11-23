package com.feature.auction.domain.usecase

import com.feature.auction.domain.models.SocialsResponse
import com.feature.auction.domain.models.SocialSignInRequest
import com.feature.auction.domain.models.SignInRequest
import com.feature.auction.domain.models.SignInResponse
import com.feature.auction.domain.repository.AuthRepo
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repoImpl:AuthRepo)  {
    suspend operator fun invoke(signInRequest: SignInRequest): Result<SignInResponse> {
        return repoImpl.signIn(signInRequest)
    }

//    suspend fun signIn(signInRequest: SignInRequest): Result<SignInResponse> {
//        return repoImpl.signIn(signInRequest)
//    }

    suspend fun googleSignIn(signInRequest: SocialSignInRequest): SocialsResponse {
        return repoImpl.signInGoogle(signInRequest)
    }

    suspend fun facebookSignIn(signInRequest: SocialSignInRequest): SocialsResponse {
        return repoImpl.signInFacebook(signInRequest)
    }
}

