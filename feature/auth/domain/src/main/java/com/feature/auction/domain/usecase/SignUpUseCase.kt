package com.feature.auction.domain.usecase

import com.feature.auction.domain.models.LegalRequest
import com.feature.auction.domain.models.SignUpRequest
import com.feature.auction.domain.models.SignUpResponse
import com.feature.auction.domain.models.SocialsRequest
import com.feature.auction.domain.models.SocialsResponse
import com.feature.auction.domain.repository.AuthRepo
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repoImpl:AuthRepo)  {

//    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
//        return repoImpl.signupIndividual(signUpRequest)
//    }
    suspend operator fun invoke(signUpRequest: SignUpRequest): Result<SignUpResponse> {
        return repoImpl.signupIndividual(signUpRequest)
    }

    suspend fun googleSignUp(signUpRequest: SocialsRequest): SocialsResponse {
        return repoImpl.signupGoogle( signUpRequest)
    }

    suspend fun facebookSignUp(signUpRequest: SocialsRequest): SocialsResponse {
        return repoImpl.signupFacebook( signUpRequest)
    }

//    suspend fun signUpLegal(signUpRequest: LegalRequest): SignUpResponse {
//        return repoImpl.signupLegal(signUpRequest)
//    }

    suspend operator fun invoke(signUpRequest: LegalRequest): Result<SignUpResponse> {
        return repoImpl.signupLegal(signUpRequest)
    }

}

