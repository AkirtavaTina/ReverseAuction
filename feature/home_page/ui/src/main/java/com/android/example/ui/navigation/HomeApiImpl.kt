package com.android.example.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.core.feature_api.HomeApi
import javax.inject.Inject


class HomeApiImpl @Inject constructor(): HomeApi {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        val a = InternalHomePageApi()
        a.registerGraph(
            navController, navGraphBuilder
        )
    }
}