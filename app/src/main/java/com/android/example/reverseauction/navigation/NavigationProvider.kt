package com.android.example.reverseauction.navigation

import com.core.feature_api.FeatureApi
import com.core.feature_api.HomeApi
import com.core.feature_api.ProfileApi

data class NavigationProvider(
    val authApi : FeatureApi,
    val homeApi : HomeApi,
    val profileApi: ProfileApi
)
