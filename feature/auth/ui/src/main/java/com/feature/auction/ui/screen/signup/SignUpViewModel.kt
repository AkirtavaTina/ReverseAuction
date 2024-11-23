package com.feature.auction.ui.screen.signup

import android.icu.lang.UCharacter.toLowerCase
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.core.common.local.JwtTokenDataStore
import com.core.common.navigation_constants.AuthFeature
import com.core.common.navigation_constants.HomePage
import com.feature.auction.domain.models.LegalRequest
import com.feature.auction.domain.models.SignUpRequest
import com.feature.auction.domain.models.SocialsRequest
import com.feature.auction.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val jwtTokenDataStore: JwtTokenDataStore
) : ViewModel() {

    private var _state = MutableStateFlow(SignUpStates())
    val state: StateFlow<SignUpStates> = _state.asStateFlow()

    fun isEmailValid(email: String): String? {
        val valid = when {
            email.isEmpty() -> "Email cannot be empty."
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format."
            else -> null
        }
        return valid
    }

    fun validatePassword(password: String): String? {
        val valid = when {
            password.isEmpty() -> "Password cannot be empty."
            password.length < 8 -> "This password is too short. It must contain at least 8 characters."
            password.all { it.isDigit() } -> "This password is entirely numeric."
            password.all { it.isLetter() } -> "The password cannot consist only of alphabetic characters."
            password.all { !it.isLetterOrDigit() } -> "The password cannot consist only of special characters."
            password.none { it.isUpperCase() } -> "The password must contain at least one uppercase letter."
            password.none { !it.isLetterOrDigit() } -> "The password must contain at least one special character."
            else -> null
        }
        return valid;
    }


    fun validate(checkField: String): String? {
        val valid = when {
            checkField.isEmpty() -> "Name cannot be empty."
            else -> null
        }
        return valid;
    }


    fun validateConfirmPassword(confirmPassword: String, originalPassword: String): String? {
        return when {
            confirmPassword.isEmpty() -> "Confirm password cannot be empty."
            confirmPassword != originalPassword -> "Passwords do not match."
            else -> null
        }
    }


    fun changeStringField(fieldsEnum: FieldsEnum, value: String) {
        when (fieldsEnum) {
            FieldsEnum.ROLE -> _state.update { it.copy(role = value) }
            FieldsEnum.NAME -> _state.update { it.copy(name = value) }
            FieldsEnum.SURNAME -> _state.update { it.copy(surname = value) }
            FieldsEnum.LEGALNAME -> _state.update { it.copy(legalName = value) }
            FieldsEnum.IDCODE -> _state.update { it.copy(idCode = value) }
            FieldsEnum.EMAIL -> _state.update { it.copy(email = value) }
            FieldsEnum.PASSWORD -> _state.update { it.copy(password = value) }
            FieldsEnum.CONFIRMPASSWORD -> _state.update { it.copy(confirmPassword = value) }
            FieldsEnum.SELECTEDITEM -> _state.update { it.copy(selectedItem = value) }
            FieldsEnum.USERID -> _state.update { it.copy(userId = value) }
            else -> {}
        }
    }

    fun changeBooleanField(fieldsEnum: FieldsEnum, value: Boolean) {
        when (fieldsEnum) {
            FieldsEnum.PASSWORDVISIBLE -> _state.update { it.copy(passwordVisible = value) }
            FieldsEnum.EXPANDED -> _state.update { it.copy(expanded = value) }
            FieldsEnum.CONFIRMEDPASSWORDVISIBLE -> _state.update { it.copy(confirmedPasswordVisible = value) }
            FieldsEnum.ISCHECKED -> _state.update { it.copy(isChecked = value) }
            FieldsEnum.ISNAMEVALID -> _state.update { it.copy(isNameValid = value) }
            FieldsEnum.ISSURNAMEVALID -> _state.update { it.copy(isSurnameValid = value) }
            FieldsEnum.ISEMAILVALID -> _state.update { it.copy(isEmailValid = value) }
            FieldsEnum.ISPASSWORDVALID -> _state.update { it.copy(isPasswordValid = value) }
            FieldsEnum.ISCONFIRMATIONVALID -> _state.update { it.copy(isConfirmationValid = value) }
            FieldsEnum.ISSELECTIONEMPTY -> _state.update { it.copy(isSelectionEmpty = value) }
            FieldsEnum.ISLEGALNAMEEMPTY -> _state.update { it.copy(isLegalNameValid = value) }
            FieldsEnum.ISIDEMPTY -> _state.update { it.copy(isIdValid = value) }
            else -> {}
        }
    }

    fun changeIndex(fieldsEnum: FieldsEnum, value: Int) {
        when (fieldsEnum) {
            FieldsEnum.SELECTEDINDEX -> _state.update { it.copy(selectedIndex = value) }
            else -> {}
        }
    }

    fun signup(navController: NavController) {
        viewModelScope.launch {
            checkStates()
            val name = state.value.name
            val surname = state.value.surname
            val email = state.value.email
            val password = state.value.password
            val confirmPassword = state.value.confirmPassword
            val country = state.value.selectedItem
            val legalName = state.value.legalName
            val idCode = state.value.idCode

            val passValid = (validatePassword(password) == null)
            val nameValid = (validate(name) == null)
            val surnameValid = (validate(surname) == null)
            val emailValid = isEmailValid(email) == null
            val confValid =
                validateConfirmPassword(confirmPassword = confirmPassword, password) == null
            _state.update { it.copy(isPasswordValid = passValid) }
            _state.update { it.copy(isNameValid = nameValid) }
            _state.update { it.copy(isSurnameValid = surnameValid) }
            _state.update { it.copy(isEmailValid = emailValid) }
            _state.update { it.copy(isConfirmationValid = confValid) }


            if (((nameValid && surnameValid) || (legalName.isNotEmpty() && idCode.isNotEmpty())) && emailValid && passValid && country.isNotEmpty() && confValid)
                try {
                    val signUpResult = if (nameValid) {
                        signUpUseCase(
                            signUpRequest = SignUpRequest(
                                toLowerCase(email), password, state.value.role, country,
                                SignUpRequest.IndividualProfile(name, surname)
                            )
                        )
                    } else {
                        signUpUseCase(
                            signUpRequest = LegalRequest(
                                toLowerCase(email), password, state.value.role, country,
                                LegalRequest.LegalProfile(legalName, idCode)
                            )
                        )
                    }

                    signUpResult.onSuccess { result ->
                        val userId = result.userId
                        if (userId != null) {
                            changeStringField(FieldsEnum.USERID, userId)
                        }
                        navController.navigate("${AuthFeature.verification}/$userId/$email/verify")
                    }
                    signUpResult.onFailure { result ->
                        val errorCode = result.message
                        if (errorCode == "400") {
                            _state.update {
                                it.copy(
                                    isEmailValid = false,
                                )
                            }
                        }
                    }

                } catch (e: HttpException) {
                    Log.e("SignUpViewModel", "SignUp failed2: ${e.message}")
                }
        }
    }

    private fun checkStates() {
        changeBooleanField(
            fieldsEnum = FieldsEnum.ISNAMEVALID,
            _state.value.name.isNotEmpty()
        )
        changeBooleanField(
            fieldsEnum = FieldsEnum.ISSURNAMEVALID,
            _state.value.surname.isNotEmpty()
        )
        changeBooleanField(
            fieldsEnum = FieldsEnum.ISLEGALNAMEEMPTY,
            _state.value.legalName.isNotEmpty()
        )
        changeBooleanField(
            fieldsEnum = FieldsEnum.ISIDEMPTY,
            _state.value.idCode.isNotEmpty()
        )
        changeBooleanField(
            fieldsEnum = FieldsEnum.ISEMAILVALID,
            _state.value.email.isNotEmpty()
        )
        changeBooleanField(
            fieldsEnum = FieldsEnum.ISCONFIRMATIONVALID,
            (validateConfirmPassword(
                _state.value.confirmPassword,
                _state.value.password
            ) != null)
        )
        changeBooleanField(
            fieldsEnum = FieldsEnum.ISSELECTIONEMPTY,
            _state.value.selectedItem.isEmpty()
        )
    }

    fun googleAuth(idToken: String, role: String, navController: NavController) {
        viewModelScope.launch {
            try {
                val googleSignUpResult = signUpUseCase.googleSignUp(
                    signUpRequest = SocialsRequest(idToken, role)
                )
                jwtTokenDataStore.saveUserInfo(
                    googleSignUpResult.refresh,
                    googleSignUpResult.access
                )
                navController.navigate("${ HomePage.homePage }/$role")
            } catch (e: HttpException) {
                Log.e("error", e.message())
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null) {
                    Log.e("error", errorBody)
                }
            }
        }
    }
}