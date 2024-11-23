package com.feature.auction.ui.screen.passRest.forgotPass

import android.icu.lang.UCharacter.toLowerCase
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.core.common.local.JwtTokenDataStore
import com.core.common.navigation_constants.AuthFeature
import com.feature.auction.domain.models.PassResetRequest
import com.feature.auction.domain.usecase.PassResetUseCase
import com.feature.auction.ui.screen.signin.FieldsEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val passResetUseCase: PassResetUseCase,
                                                  private val jwtTokenDataStore: JwtTokenDataStore
) :
    ViewModel() {

    private var _state = MutableStateFlow(ForgotPassStates())
    val state: StateFlow<ForgotPassStates> = _state.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _state.update {
            it.copy(email = newEmail)
        }
        _state.update {
            it.copy(error = false)
        }
        validateEmail(newEmail)
    }

    private fun isEmailValid(email: String): String? {
        val valid = when {
            email.isEmpty() -> "Email cannot be empty."
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format."
            else -> null
        }
        return valid
    }

    private fun validateEmail(email: String) {
        _state.update {
            it.copy(isEmailValid =  email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        }
    }

    fun sendResetCode(navController: NavController) {
        viewModelScope.launch {
            _state.update { it.copy(isEmailValid = isEmailValid(state.value.email) == null) }
            val email = state.value.email

            if (email.isEmpty()) {
                _state.update {
                    it.copy(isEmailValid = email.isNotEmpty())
                }
            } else {
                try {
                    val passReset = passResetUseCase(passResetRequest = PassResetRequest(toLowerCase(email)))
                    passReset.onSuccess { result ->
                        val userId = result.userId

                        // Optionally retrieve role if needed
                        val userProfile = jwtTokenDataStore.getUserProfileFromAccessToken()
                        userProfile?.let {
                            _state.update { it.copy(userRole = userProfile.role) }
                        }

                        navController.navigate("${AuthFeature.verification}/${userId}/${email}/resetPassword")
                    }
                    passReset.onFailure { result ->
                        if (result.message == "400") {
                            _state.update {
                                it.copy(
                                    errorMessage = "No active account found with the given credentials",
                                    error = true
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d("forgotpassviewmodel ", "${e.message}")
                }
            }
        }
    }

}


