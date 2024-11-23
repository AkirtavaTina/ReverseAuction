package com.android.example.ui.PasswordChange

data class PasswordChangeStates(
    var password1: String = "",
    var password2: String = "",
    var password3: String = "",
    var passwordVisible1: Boolean = false,
    var passwordVisible2: Boolean = false,
    var passwordVisible3: Boolean = false,
    var isPasswordValid: Boolean = true,
    var isFocused1: Boolean = false,
    var isFocused2: Boolean = false,
    var isFocused3: Boolean = false,
    var error: String? = null,
    var success: String = "",
    var showPopup: Boolean = false
)
