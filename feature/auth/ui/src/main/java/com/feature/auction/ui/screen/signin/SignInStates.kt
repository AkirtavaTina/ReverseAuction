package com.feature.auction.ui.screen.signin

data class SignInStates (
    var email: String = "",
    var password: String = "",
    var passwordVisible: Boolean = false,
    var isEmailValid: Boolean = true,
    var isPasswordValid: Boolean = true,
    var error: String = "",
    var errorMessage: String = "",
    var role: String = "",
)