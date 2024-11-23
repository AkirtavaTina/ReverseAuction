package com.android.example.ui.auctions

import com.android.example.domain.model.Category

data class AuctionsState(
    var isFocused: Boolean = false,
    var title: String = "",
    var expandCategory: Boolean = false,
    var selectCategory: String = "",
    var isCategoryEmpty: Boolean = false,
    var isFocused2: Boolean = false,
    var title2: String = "",
    var expandSubcategory: Boolean = false,
    var selectSubcategory: String = "",
    var isSubcategoryEmpty: Boolean = false,
    val categories: List<Category> = emptyList(),
    val subCategories: List<String?> = emptyList(),
    var selectedCategoryId: Int? = null,
    var description:String = "",
    var currencyExpanded:Boolean = false,
    var currency: String = "USD",
    var budget: String = "",
    var isChecked: Boolean = false,
    var expanded: Boolean = false,
    var isSelectionEmpty: Boolean = false,
    var selectedItem: String = "",
    var phoneNumber: String = "",
    )