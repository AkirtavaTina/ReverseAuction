package com.feature.auction.ui.screen.passRest.forgotPass

data class ForgotPassStates (
    var email: String = "",
    var isEmailValid: Boolean = false,
    var error: Boolean = false,
    var errorMessage: String = "",
    var userRole: String = ""
)