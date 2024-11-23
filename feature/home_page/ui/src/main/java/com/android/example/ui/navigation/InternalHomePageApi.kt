package com.android.example.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.android.example.ui.auctions.AddAuction
import com.android.example.ui.screen.HomePage
import com.core.common.navigation_constants.HomePage
import com.core.feature_api.HomeApi
import javax.inject.Inject

internal class InternalHomePageApi  @Inject constructor() : HomeApi {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun registerGraph(
        navController: androidx.navigation.NavHostController,
        navGraphBuilder: androidx.navigation.NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            startDestination = HomePage.homePage,
            route = HomePage.nestedRoute
        ) {
//            composable(
//                route = "${AuthFeature.verification}/{userId}/{email}/{type}",
//                arguments = listOf(
//                    navArgument("userId") { type = NavType.StringType },
//                    navArgument("email") { type = NavType.StringType },
//                    navArgument("type") { type = NavType.StringType },
//                    navArgument("role") { type = NavType.StringType }
//                )) { backStackEntry ->
//                val userId = backStackEntry.arguments?.getString("userId")
//                val email = backStackEntry.arguments?.getString("email")
//                val type = backStackEntry.arguments?.getString("type")
//                val role = backStackEntry.arguments?.getString("role")
//
//                if (role != null) {
//                    VerificationPage(
//
            composable(route = "${HomePage.homePage}/{role}",
                arguments = listOf(
                    navArgument("role") { type = NavType.StringType }))
            { backStackEntry ->
                val role = backStackEntry.arguments?.getString("role")

                if (role != null) {
                    HomePage(
                        navController =  navController, role = role
                    )
                }
            }
            composable(HomePage.addAction){
                AddAuction(
                    navController =  navController
                )
            }
        }
    }
}