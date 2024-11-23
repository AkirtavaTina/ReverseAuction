package com.android.example.ui.auctions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.domain.usecase.AuctionsUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuctionsViewModel@Inject constructor(
    private val auctionsUseCase: AuctionsUseCase
) : ViewModel(){
    private var _state = MutableStateFlow(AuctionsState())
    val state: StateFlow<AuctionsState> = _state.asStateFlow()

    init {
        fetchSuperCategories()
    }

    private fun fetchSuperCategories() {
        viewModelScope.launch {
            auctionsUseCase.getSuperCategories().collect { categories ->
                _state.update { currentState ->
                    currentState.copy(categories = categories) // Store the full Category objects
                }
            }
        }
    }


    fun fetchSubCategories(categoryId: Int) {
        viewModelScope.launch {
            val subCategories = auctionsUseCase.getSubCategories(categoryId)
            _state.update {
                subCategories.map { it.categoryName }
                    .let { it1 -> it.copy(subCategories = it1) }
            }
        }
    }

    fun changeStringField(auctionsEnum: AuctionsEnum, value: String) {
        when (auctionsEnum) {
            AuctionsEnum.TITLE -> _state.update { it.copy(title = value) }
            AuctionsEnum.SELECTCATEGORY -> _state.update { it.copy(selectCategory = value) }
            AuctionsEnum.TITLE2 -> _state.update { it.copy(title2 = value) }
            AuctionsEnum.SELECTSUBCATEGORY -> _state.update { it.copy(selectSubcategory = value) }
            AuctionsEnum.DESCRIPTION -> _state.update { it.copy(description = value) }
            AuctionsEnum.CURRENCY -> _state.update { it.copy(currency = value) }
            AuctionsEnum.BUDGET -> _state.update { it.copy(budget = value) }
            else -> {}
        }
    }

    fun changeBooleanField(auctionsEnum: AuctionsEnum, value: Boolean) {
        when (auctionsEnum) {
            AuctionsEnum.ISFOCUSED -> _state.update { it.copy(isFocused = value) }
            AuctionsEnum.ISCHECKED -> _state.update { it.copy(isChecked = value) }
            AuctionsEnum.EXPANDCATEGORY -> _state.update { it.copy(expandCategory = value) }
            AuctionsEnum.ISCATEGORYEMTY -> _state.update { it.copy(isCategoryEmpty = value) }
            AuctionsEnum.ISFOCUSED2 -> _state.update { it.copy(isFocused2 = value) }
            AuctionsEnum.EXPANDSUBCATEGORY -> _state.update { it.copy(expandSubcategory = value) }
            AuctionsEnum.ISSUBCATEGORYEMTY -> _state.update { it.copy(isSubcategoryEmpty = value) }
            AuctionsEnum.CURRENCYEXPANDED -> _state.update { it.copy(currencyExpanded = value) }
            else -> {}
        }
    }
}