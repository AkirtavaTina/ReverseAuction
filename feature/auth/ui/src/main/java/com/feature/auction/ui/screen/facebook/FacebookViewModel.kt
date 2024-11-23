package com.feature.auction.ui.screen.facebook

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.core.common.local.JwtTokenDataStore
import com.core.common.navigation_constants.HomePage
import com.feature.auction.domain.models.SocialSignInRequest
import com.feature.auction.domain.models.SocialsRequest
import com.feature.auction.domain.usecase.SignInUseCase
import com.feature.auction.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class FacebookViewModel @Inject constructor(private val signInUseCase: SignInUseCase, private val signUpUseCase: SignUpUseCase, private val jwtTokenDataStore: JwtTokenDataStore): ViewModel() {
    fun signIn(navController: NavController, token: String){
        viewModelScope.launch {
            try {
                val googleSignUpResult = signInUseCase.facebookSignIn(
                    signInRequest = SocialSignInRequest(token)
                )
                var role = ""
                jwtTokenDataStore.saveUserInfo(
                    googleSignUpResult.refresh,
                    googleSignUpResult.access
                )
                // Retrieve user profile with role after saving tokens
                val userProfile = jwtTokenDataStore.getUserProfileFromAccessToken()
                userProfile?.let { profile ->
                     role = profile.role
                }
                navController.navigate("${ HomePage.homePage }/${role}")
            } catch (e: HttpException) {
                Log.e("error", e.message())
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null) {
                    Log.e("error", errorBody)
                }
            }
        }
    }

    fun signUp(navController: NavController, token: String, role: String){
        viewModelScope.launch {
            try {
                val googleSignUpResult = signUpUseCase.facebookSignUp(
                    signUpRequest = SocialsRequest(token, role)
                )
                jwtTokenDataStore.saveUserInfo(
                    googleSignUpResult.refresh,
                    googleSignUpResult.access
                )
                navController.navigate(HomePage.homePage)
            } catch (e: HttpException) {
                Log.e("error", e.message())
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null) {
                    Log.e("error", errorBody)
                }
            }
        }
    }
}

