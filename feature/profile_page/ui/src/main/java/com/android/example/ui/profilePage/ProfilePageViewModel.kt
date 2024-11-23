package com.android.example.ui.profilePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.core.common.local.JwtTokenDataStore
import com.core.common.navigation_constants.AuthFeature
import com.core.common.navigation_constants.HomePage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.core.common.navigation_constants.ProfilePage
import com.feature.auction.domain.models.LogoutRequest
import com.feature.auction.domain.usecase.ProfileUseCase
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class ProfilePageViewModel @Inject constructor(
    private val jwtTokenDataStore: JwtTokenDataStore,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(ProfilePageStates())
    val state: StateFlow<ProfilePageStates> = _state.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            val userProfile = jwtTokenDataStore.getUserProfileFromAccessToken()
            userProfile?.let {
                _state.update { currentState ->
                    currentState.copy(
                        profileName = it.profileName,
                        verified = it.isVerified
                    )
                }
            }
        }
    }

    fun changeBooleanField(value: Boolean) {
        _state.update { it.copy(verified = value) }
    }

    fun logout(navController: NavController) {
        viewModelScope.launch {
            _state.value = ProfilePageStates()
            val refreshToken = jwtTokenDataStore.getRefreshJwt().firstOrNull()
            if (refreshToken != null) {
                profileUseCase.logout(logoutRequest = LogoutRequest(refreshToken))
                jwtTokenDataStore.clearAllTokens()
            }
            navController.navigate(AuthFeature.signIn){
                popUpTo(ProfilePage.nestedRoute) { inclusive = true }
                popUpTo(HomePage.nestedRoute) { inclusive = true }
            }

        }
    }
}
