package com.feature.auction.ui.screen.passRest.resetPassword

data class ResetPassStates(
    var newPass: String = "",
    var confirmedPass: String = "",
    var newPasswordVisible: Boolean = false,
    var confirmedPasswordVisible: Boolean = false,
    var isPasswordValid: Boolean = true,
    var isConfirmationValid: Boolean = true,
    var errorMessage: String = ""
)
