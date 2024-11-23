package com.android.example.ui.settings

import androidx.lifecycle.ViewModel
import com.android.example.ui.FieldsEnum
import com.core.common.local.JwtTokenDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val jwtTokenDataStore: JwtTokenDataStore,
//    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    private var _state = MutableStateFlow(SettingsStates())
    val state: StateFlow<SettingsStates> = _state.asStateFlow()

    fun changeStringField(fieldsEnum: FieldsEnum, value: String) {
        when (fieldsEnum) {
            FieldsEnum.SELECTEDITEM3 -> _state.update { it.copy(selectedItem3 = value) }
            else -> {}
        }
    }

    fun changeBooleanField(fieldsEnum: FieldsEnum, value: Boolean) {
        when (fieldsEnum) {
            FieldsEnum.EXPANDED3 -> _state.update { it.copy(expanded3 = value) }
            FieldsEnum.ISSELECTIONEMPTY3 -> _state.update { it.copy(isSelectionEmpty3 = value) }
            FieldsEnum.MFAACTIVE -> _state.update { it.copy(mfaActive = value) }
            else -> {}
        }
    }
}