package com.feature.auction.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.core.feature_api.FeatureApi
import javax.inject.Inject


class AuthApiImpl @Inject constructor(): FeatureApi {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        val a = InternalAuthFeatureApi()
        a.registerGraph(
            navController, navGraphBuilder
        )
    }
}
