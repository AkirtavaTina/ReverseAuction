package com.android.example.ui.deleteAccount

data class DeleteAccountState(
    var password: String = "",
    var passwordVisible: Boolean = false,
    var isPasswordValid: Boolean = true,
    var isFocused: Boolean = false,
    var error: String = "",
    var showDialog: Boolean = false,
)
