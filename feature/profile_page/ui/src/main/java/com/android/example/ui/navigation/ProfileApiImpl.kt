package com.android.example.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.core.feature_api.ProfileApi
import javax.inject.Inject


class ProfileApiImpl @Inject constructor(): ProfileApi {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        val a = InternalProfilePageApi()
        a.registerGraph(
            navController, navGraphBuilder
        )
    }
}