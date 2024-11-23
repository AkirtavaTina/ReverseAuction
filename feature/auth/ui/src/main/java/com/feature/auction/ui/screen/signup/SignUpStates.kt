package com.feature.auction.ui.screen.signup

data class SignUpStates(
    var name: String = "",
    var surname: String = "",
    var legalName: String = "",
    var idCode: String = "",
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var role: String = "",

    // State for visibility toggles
    var passwordVisible: Boolean = false,
    var confirmedPasswordVisible: Boolean = false,

    // State for tab selection
    var selectedIndex: Int = 0,

    // State for dropdown menu
    var selectedItem: String = "",
    var expanded: Boolean = false,

    // State for checkboxes and errors
    var isChecked: Boolean = false,
    var isNameValid: Boolean = true,
    var isSurnameValid: Boolean = true,
    var isLegalNameValid: Boolean = true,
    var isIdValid: Boolean = true,
    var isPasswordValid: Boolean = true,
    var isConfirmationValid: Boolean = true,
    var isSelectionEmpty: Boolean = false,
    val isEmailValid: Boolean = true,
    val emailErrorMessage: String? = null,


    var userId: String? = "",

    var nameError: String? = "",
    var surnameError: String? = "",
    var emailError: String? = "",
    var passwordError: String? = "",
    var confirmPasswordError: String? = "",
    var countryError: String? = "",
//    val authCode: String? = "",
)

