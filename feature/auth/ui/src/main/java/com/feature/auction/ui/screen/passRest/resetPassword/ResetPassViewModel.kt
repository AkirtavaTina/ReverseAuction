package com.feature.auction.ui.screen.passRest.resetPassword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.core.common.navigation_constants.AuthFeature
import com.feature.auction.domain.models.ConfirmResetRequest
import com.feature.auction.domain.usecase.PassResetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPassViewModel @Inject constructor(private val passResetUseCase: PassResetUseCase) :
    ViewModel() {
    private var _state = MutableStateFlow(ResetPassStates())
    val state: StateFlow<ResetPassStates> = _state.asStateFlow()

    fun onPasswordChanged(newPass: String) {
        _state.update { it.copy(newPass = newPass) }
        _state.update { it.copy(errorMessage = "") }
    }

    fun onConfirmChanged(confirmedPass: String) {
        _state.update { it.copy(confirmedPass = confirmedPass) }
        _state.update { it.copy(errorMessage = "") }
    }

    fun passVisible(newPass: Boolean) {
        _state.update { it.copy(newPasswordVisible = newPass) }
    }

    fun confirmedVisible(newPass: Boolean) {
        _state.update { it.copy(confirmedPasswordVisible = newPass) }
    }

    fun validatePassword(password: String): String? {
        val valid = when {
            password.isEmpty() -> "Password cannot be empty."
            password.length < 8 -> "This password is too short. It must contain at least 8 characters."
            password.all { it.isDigit() } -> "This password is entirely numeric."
            password.all { it.isLetter() } -> "The password cannot consist only of alphabetic characters."
            password.all { !it.isLetterOrDigit() } -> "The password cannot consist only of special characters."
            password.none { it.isUpperCase() } -> "The password must contain at least one uppercase letter."
            password.none { !it.isLetterOrDigit() } -> "The password must contain at least one special character."
            else -> null
        }
        return valid;
    }

    fun validateConfirmPassword(confirmPassword: String, originalPassword: String): String? {
        return when {
            confirmPassword.isEmpty() -> "Confirm password cannot be empty."
            confirmPassword != originalPassword -> "Passwords do not match."
            else -> null
        }
    }

    fun resetPassword(
        newPass: String,
        confPassword: String,
        token: String?,
        uidb: String?,
        navController: NavController
    ) {
        viewModelScope.launch {
            _state.update { it.copy(newPass = newPass) }
            _state.update { it.copy(confirmedPass = confPassword) }
            val password = state.value.newPass
            val confirmPassword = state.value.confirmedPass
            val passValid = (validatePassword(password) == null)
            val confValid =
                validateConfirmPassword(confirmPassword = confirmPassword, password) == null
            _state.update { it.copy(isPasswordValid = passValid) }
            _state.update { it.copy(isConfirmationValid = confValid) }

            if (confValid && passValid) {
                try {
                    val resetResult =
                        passResetUseCase(
                            token, uidb, ConfirmResetRequest(newPass, confPassword)
                        )
                    resetResult.onSuccess { result ->
                        result.message?.let { Log.d("saqsesia?", it) }
                        navController.navigate(AuthFeature.success)
                    }
                    resetResult.onFailure { result ->
                        if (result.message == "400") {
                            _state.update { it.copy(errorMessage = "Password is the same as old one.") }
                            _state.update { it.copy(isPasswordValid = false) }
                            _state.update { it.copy(isConfirmationValid = false) }
                        }
                    }

                } catch (e: Exception) {
                    Log.e("ResetViewModel", "Reset failed: ${e.message}")

                }
            }
        }
    }
}