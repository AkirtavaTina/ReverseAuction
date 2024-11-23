package com.android.example.ui.profileInfo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.domain.model.ProfileInfo
import com.android.example.domain.usecase.ProfileUseCase
import com.android.example.ui.FieldsEnum
import com.core.common.local.JwtTokenDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ProfileInfoViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val dataStore: JwtTokenDataStore
) : ViewModel() {
    private var _state = MutableStateFlow(ProfileInfoStates())
    val state: StateFlow<ProfileInfoStates> = _state.asStateFlow()

    private var _profileState = MutableStateFlow<ProfileInfo?>(null)
    val profileState: StateFlow<ProfileInfo?> = _profileState

    @RequiresApi(Build.VERSION_CODES.O)
    private val formattedDatePattern = DateTimeFormatter.ofPattern("MM/dd/yy")


    init {
        getProfileData()
    }

    private fun getProfileData() {
        viewModelScope.launch {
            val accessToken = dataStore.getAccessJwt().firstOrNull()
            if (accessToken != null) {
                Log.d("getAccessToken1", accessToken)
            }
            if (accessToken != null) {
                try {
                    val profileData = profileUseCase.getUserProfile(accessToken)
                    _profileState.value = profileData
                    _state.update {
                        it.copy(
                            firstName = profileData?.firstName ?: "",
                            lastName = profileData?.lastName ?: "",
                            email = profileData?.user?.email ?: "",
                            idNumber = profileData?.idNumber ?: "",
                        )
                    }
//                    profileData?.password?.let { Log.d("password", it) }

                } catch (e: Exception) {
                    // Handle exception
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun changeStringField(fieldsEnum: FieldsEnum, value: String) {
        when (fieldsEnum) {
            FieldsEnum.SELECTEDITEM -> _state.update { it.copy(selectedItem = value) }
            FieldsEnum.SELECTEDITEM2 -> _state.update { it.copy(selectedItem2 = value) }
            FieldsEnum.FIRSTNAME -> _state.update { it.copy(firstName = value) }
            FieldsEnum.LASTNAME -> _state.update { it.copy(lastName = value) }
            FieldsEnum.EMAIL -> _state.update { it.copy(email = value) }
            FieldsEnum.IDNUMBER -> _state.update { it.copy(idNumber = value) }
            FieldsEnum.PHONENUMBER -> _state.update { it.copy(phoneNumber = value) }
            FieldsEnum.DATE -> {
                _state.update { it.copy(date = value) }
                _state.update { it.copy(formattedDate = formatDate(value)) }
            }            FieldsEnum.FORMATEDDATE -> _state.update { it.copy(formattedDate = value) }
            else -> {}
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDate(value: String): String {
        return try {
            val date = LocalDate.parse(value, DateTimeFormatter.ofPattern("MMddyy"))
            date.format(formattedDatePattern)
        } catch (e: Exception) {
            ""
        }
    }


    fun changeBooleanField(fieldsEnum: FieldsEnum, value: Boolean) {
        when (fieldsEnum) {
            FieldsEnum.EXPANDED -> _state.update { it.copy(expanded = value) }
            FieldsEnum.ISSELECTIONEMPTY -> _state.update { it.copy(isSelectionEmpty = value) }
            FieldsEnum.EXPANDED2 -> _state.update { it.copy(expanded2 = value) }
            FieldsEnum.ISSELECTIONEMPTY2 -> _state.update { it.copy(isSelectionEmpty2 = value) }
            FieldsEnum.ISFOCUSED4 -> _state.update { it.copy(isFocused4 = value) }
            FieldsEnum.ISFOCUSED5 -> _state.update { it.copy(isFocused5 = value) }
            FieldsEnum.ISFOCUSED6 -> _state.update { it.copy(isFocused6 = value) }
            FieldsEnum.ISFOCUSED7 -> _state.update { it.copy(isFocused7 = value) }
            FieldsEnum.ISFOCUSED8 -> _state.update { it.copy(isFocused8 = value) }
            FieldsEnum.ISFOCUSED9 -> _state.update { it.copy(isFocused9 = value) }
            else -> {}
        }
    }
}