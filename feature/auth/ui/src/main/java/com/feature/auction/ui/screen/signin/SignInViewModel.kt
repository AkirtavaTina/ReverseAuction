package com.feature.auction.ui.screen.signin

import android.icu.lang.UCharacter.toLowerCase
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.core.common.local.JwtTokenDataStore
import com.core.common.navigation_constants.AuthFeature
import com.core.common.navigation_constants.HomePage
import com.feature.auction.domain.models.SignInRequest
import com.feature.auction.domain.models.SocialSignInRequest
import com.feature.auction.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val jwtTokenDataStore: JwtTokenDataStore
) : ViewModel() {

    private var _state = MutableStateFlow(SignInStates())
    val state: StateFlow<SignInStates> = _state.asStateFlow()

    fun isEmailValid(email: String): String? {
        val valid = when {
            email.isEmpty() -> "Email cannot be empty."
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format."
            else -> null
        }
        return valid
    }

    fun changeStringField(fieldsEnum: FieldsEnum, value: String) {
        when (fieldsEnum) {
            FieldsEnum.EMAIL -> _state.update { it.copy(email = value) }
            FieldsEnum.PASSWORD -> _state.update { it.copy(password = value) }
            FieldsEnum.ERRORMESSAGE -> _state.update { it.copy(errorMessage = value) }
            else -> {}
        }
    }

    fun changeBooleanField(fieldsEnum: FieldsEnum, value: Boolean) {
        when (fieldsEnum) {
            FieldsEnum.ISEMAILEMPTY -> _state.update { it.copy(isEmailValid = value) }
            FieldsEnum.PASSWORDVISIBLE -> _state.update { it.copy(passwordVisible = value) }
            FieldsEnum.ISPASSWORDEMPTY -> _state.update { it.copy(isPasswordValid = value) }
            else -> {}
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun signIn(navController: NavController) {
        viewModelScope.launch {
            changeBooleanField(FieldsEnum.ISEMAILEMPTY, isEmailValid(state.value.email) == null)
            changeBooleanField(FieldsEnum.ISPASSWORDEMPTY, state.value.password.isNotEmpty())
            val email = state.value.email
            val password = state.value.password
            if (email.isEmpty() || password.isEmpty()) {
                _state.update {
                    it.copy(
                        isEmailValid = email.isNotEmpty(),
                        isPasswordValid = password.isNotEmpty()
                    )
                }
            } else {
                val loginResult =
                    signInUseCase(signInRequest = SignInRequest(toLowerCase(email), password))
                loginResult.onSuccess { result ->
                    result.refresh?.let {
                        result.access?.let { accessToken ->
                            jwtTokenDataStore.saveUserInfo(it, accessToken)

                            // Retrieve user profile with role after saving tokens
                            val userProfile = jwtTokenDataStore.getUserProfileFromAccessToken()
                            userProfile?.let { profile ->
                                _state.update { it.copy(role = profile.role) } // Assuming SignInStates has a userRole field
                            }
                        }
                    }
                    navController.navigate("${ HomePage.homePage }/${state.value.role}") {
                        popUpTo(AuthFeature.nestedRoute) { inclusive = true }
                    }
                    _state.update {
                        it.copy(isEmailValid = true, isPasswordValid = true)
                    }
                }
                loginResult.onFailure { loginResultData ->
                    val userId = loginResultData.message
                    if (userId != "401") navController.navigate("${AuthFeature.verification}/$userId/$email/verify")
                    else {
                        _state.update { it.copy(error = "No active account found with the given credentials") }
                        _state.update {
                            it.copy(
                                isEmailValid = false,
                                isPasswordValid = false
                            )
                        }
                    }
                }
            }
        }
    }


    fun googleAuth(idToken: String, navController: NavController) {
        viewModelScope.launch {
            try {
                val googleSignUpResult = signInUseCase.googleSignIn(SocialSignInRequest(idToken))
                jwtTokenDataStore.saveUserInfo(
                    googleSignUpResult.refresh,
                    googleSignUpResult.access
                )

                // Retrieve user profile with role after saving tokens
                val userProfile = jwtTokenDataStore.getUserProfileFromAccessToken()
                userProfile?.let { profile ->
                    _state.update { it.copy(role = profile.role) } // Assuming SignInStates has a userRole field
                }

                navController.navigate("${ HomePage.homePage }/${state.value.role}")

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null) {
                    Log.e("error", errorBody)
                }
            }
        }
    }
}
