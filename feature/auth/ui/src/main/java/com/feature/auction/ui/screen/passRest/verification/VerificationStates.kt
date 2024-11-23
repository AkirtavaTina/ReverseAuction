package com.feature.auction.ui.screen.passRest.verification

data class VerificationStates (
    var number1: String = "",
    var number2: String = "",
    var number3: String = "",
    var number4: String = "",
    var number5: String = "",
    var number6: String = "",
    var error: Boolean = false,
    var errorMessage: String = "That code wasn’t valid. Give it another go",
    var role: String = ""
)
