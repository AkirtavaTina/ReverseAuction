package com.android.example.ui.deleteAccount

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.android.example.domain.usecase.ProfileUseCase
import com.android.example.ui.FieldsEnum
import com.core.common.local.JwtTokenDataStore
import com.core.common.navigation_constants.AuthFeature
import com.core.common.navigation_constants.HomePage
import com.core.common.navigation_constants.ProfilePage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val jwtTokenDataStore: JwtTokenDataStore,
    private val profileUseCase: ProfileUseCase
): ViewModel(){
    private var _state = MutableStateFlow(DeleteAccountState())
    val state: StateFlow<DeleteAccountState> = _state.asStateFlow()

    fun changeBooleanField(fieldsEnum: FieldsEnum, value: Boolean) {
        when (fieldsEnum) {
            FieldsEnum.PASSWORDVISIBLE -> _state.update { it.copy(passwordVisible = value) }
            FieldsEnum.ISPASSWORDVALID -> _state.update { it.copy(isPasswordValid = value) }
            FieldsEnum.ISFOCUSED -> _state.update { it.copy(isFocused = value) }
            FieldsEnum.SHOWDIALOG -> _state.update { it.copy(showDialog = value) }
            else -> {}
        }
    }

    fun changeStringField(fieldsEnum: FieldsEnum, value: String) {
        when (fieldsEnum) {
            FieldsEnum.PASSWORD -> _state.update { it.copy(password = value) }
            else -> {}
        }
    }

    fun delete(password: String, navController: NavController) {
        viewModelScope.launch {
            val accessToken = jwtTokenDataStore.getAccessJwt().firstOrNull()

            val isPasswordValid = accessToken?.let { profileUseCase.checkPassword(password, it) }

            if (isPasswordValid == true){
                val isDeleted = accessToken.let { profileUseCase.deleteUser(it) }

                if (isDeleted) {
                    jwtTokenDataStore.clearAllTokens()
                }
                navController.navigate(AuthFeature.signIn){
                    popUpTo(ProfilePage.nestedRoute) { inclusive = true }
                    popUpTo(HomePage.nestedRoute) { inclusive = true }
                }
            }else {
                _state.update { it.copy(isPasswordValid = false) }
                _state.update { it.copy(error = "Password is incorrect") }
            }


        }
    }

}