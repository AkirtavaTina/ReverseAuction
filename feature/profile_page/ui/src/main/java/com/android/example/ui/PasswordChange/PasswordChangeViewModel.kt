package com.android.example.ui.PasswordChange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.domain.usecase.ProfileUseCase
import com.android.example.ui.FieldsEnum
import com.core.common.local.JwtTokenDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordChangeViewModel @Inject constructor(
    private val jwtTokenDataStore: JwtTokenDataStore,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    private var _state = MutableStateFlow(PasswordChangeStates())
    val state: StateFlow<PasswordChangeStates> = _state.asStateFlow()

    fun changeBooleanField(fieldsEnum: FieldsEnum, value: Boolean) {
        when (fieldsEnum) {
            FieldsEnum.PASSWORDVISIBLE1 -> _state.update { it.copy(passwordVisible1 = value) }
            FieldsEnum.PASSWORDVISIBLE2 -> _state.update { it.copy(passwordVisible2 = value) }
            FieldsEnum.PASSWORDVISIBLE3 -> _state.update { it.copy(passwordVisible3 = value) }
            FieldsEnum.ISPASSWORDVALID -> _state.update { it.copy(isPasswordValid = value) }
            FieldsEnum.ISFOCUSED1 -> _state.update { it.copy(isFocused1 = value) }
            FieldsEnum.ISFOCUSED2 -> _state.update { it.copy(isFocused2 = value) }
            FieldsEnum.ISFOCUSED3 -> _state.update { it.copy(isFocused3 = value) }
            FieldsEnum.SHOWPOPUP -> _state.update { it.copy(showPopup = value) }
            else -> {}
        }
    }

    fun changeStringField(fieldsEnum: FieldsEnum, value: String) {
        when (fieldsEnum) {
            FieldsEnum.PASSWORD1 -> _state.update { it.copy(password1 = value) }
            FieldsEnum.PASSWORD2 -> _state.update { it.copy(password2 = value) }
            FieldsEnum.PASSWORD3 -> _state.update { it.copy(password3 = value) }
            else -> {}
        }
    }

    fun changePassword(
        oldPassword: String,
        newPassword: String,
        newPassword2: String,
    ) {
        viewModelScope.launch {
            val accessToken = jwtTokenDataStore.getAccessJwt().firstOrNull()
            val isPasswordChanged =
                accessToken?.let {
                    profileUseCase.changePassword(oldPassword, newPassword, newPassword2,
                        it
                    )
                }

            if (isPasswordChanged == true) {
                _state.update { it.copy (success = "Password changed successfully")}
                _state.update { it.copy(showPopup = true) }
            } else {
                _state.update { it.copy (error = "Failed to change password")}
            }
        }
    }
}