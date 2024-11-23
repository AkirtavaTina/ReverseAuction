package com.feature.auction.ui.screen.passRest.verification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.core.common.local.JwtTokenDataStore
import com.core.common.navigation_constants.AuthFeature
import com.core.common.navigation_constants.HomePage
import com.feature.auction.domain.models.SignInRequest
import com.feature.auction.domain.models.VerifyRequest
import com.feature.auction.domain.usecase.VerificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val verificationUseCase: VerificationUseCase,
    private val jwtTokenDataStore: JwtTokenDataStore
) : ViewModel() {

    private var _state = MutableStateFlow(VerificationStates())
    val state: StateFlow<VerificationStates> = _state.asStateFlow()

    fun changeNumberField(fieldsEnum: FieldsEnum, value: String) {
        when (fieldsEnum) {
            FieldsEnum.NUMBER1 -> _state.update { it.copy(number1 = value) }
            FieldsEnum.NUMBER2 -> _state.update { it.copy(number2 = value) }
            FieldsEnum.NUMBER3 -> _state.update { it.copy(number3 = value) }
            FieldsEnum.NUMBER4 -> _state.update { it.copy(number4 = value) }
            FieldsEnum.NUMBER5 -> _state.update { it.copy(number5 = value) }
            FieldsEnum.NUMBER6 -> _state.update { it.copy(number6 = value) }
        }
    }

    fun verify(userId: String?, type: String?, navController: NavController) {
        viewModelScope.launch {
            val code = state.value.number1 + state.value.number2 + state.value.number3 +
                    state.value.number4 + state.value.number5 + state.value.number6
            try {
                val verifyResult = verificationUseCase(
                    verifyRequest = VerifyRequest(code),
                    userId = userId,
                    reset = type != "verify"
                )
                if (userId != null) {
                    Log.d("userId", "1: $userId")
                }

                if (type == "verify") {
                    verifyResult.onSuccess { result ->
                        result.refresh?.let {
                            result.access?.let { it1 ->
                                jwtTokenDataStore.saveUserInfo(
                                    it,
                                    it1
                                )
                            }
                        }
                        jwtTokenDataStore.getUserProfileFromAccessToken()?.let { profile ->
                            _state.update { it.copy(role = profile.role) } // Assuming SignInStates has a userRole field
                        }
                        navController.navigate("${ HomePage.homePage }/${state.value.role}")
                    }
                    verifyResult.onFailure { result ->
                        if(result.message.equals("400")){
                            _state.update { it.copy(error = true) }
                        }
                    }
                }
                if (type == "resetPassword") {
                    verifyResult.onSuccess { result ->
                        val token = result.token
                        val uidb = result.uidb64
                        navController.navigate("${AuthFeature.reset}/${token}/${uidb}")
                    }
                    verifyResult.onFailure { result ->
                        if(result.message.equals("400")){
                            _state.update { it.copy(error = true) }
                        }
                    }
                }
            } catch (e: HttpException) {
                Log.e("VerificationViewModel", "Verification failed: ${e.response()?.errorBody()?.string()}")

            }
        }
    }

    fun resend(userId: String?) {
        viewModelScope.launch {

            try {
                Log.d("userId", "2: $userId")
                val resend = verificationUseCase.resend(true, userId )
                Log.d("VerificationViewModel", "resent success: $resend")
            } catch (e: Exception) {
                Log.e("VerificationViewModel", "resent failed: ${e.message}")
            }
        }
    }
}
