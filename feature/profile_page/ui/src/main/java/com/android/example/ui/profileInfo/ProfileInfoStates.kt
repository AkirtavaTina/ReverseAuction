package com.android.example.ui.profileInfo

import android.util.Log

// Top-level function for formatting dates


data class ProfileInfoStates(
    var expanded: Boolean = false,
    var expanded2: Boolean = false,
    var selectedItem: String = "",
    var selectedItem2: String = "",
    var isSelectionEmpty: Boolean = false,
    var isSelectionEmpty2: Boolean = false,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var idNumber: String = "",
    var isFocused4: Boolean = false,
    var isFocused5: Boolean = false,
    var isFocused6: Boolean = false,
    var isFocused7: Boolean = false,
    var isFocused8: Boolean = false,
    var isFocused9: Boolean = false,
    var phoneNumber: String = "",
    var date: String = "",
    // Computed property for formatted date
    val formattedDate: String = "",

)

