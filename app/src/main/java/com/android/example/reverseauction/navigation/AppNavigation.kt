package com.android.example.reverseauction.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.core.common.navigation_constants.AuthFeature

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    navigationProvider: NavigationProvider
) {
    NavHost(navController = navController, startDestination = AuthFeature.nestedRoute){
        navigationProvider.authApi.registerGraph(
            navController, this
        )
        navigationProvider.homeApi.registerGraph(
            navController, this
        )
        navigationProvider.profileApi.registerGraph(
            navController, this
        )
    }
}