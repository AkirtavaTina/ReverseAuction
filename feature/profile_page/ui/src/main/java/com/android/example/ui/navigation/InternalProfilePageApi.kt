package com.android.example.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.android.example.ui.NotificationsPage
import com.android.example.ui.PasswordChange.PasswordChange
import com.android.example.ui.deleteAccount.DeleteAccount
import com.android.example.ui.profileInfo.ProfileInfo
import com.android.example.ui.profilePage.ProfilePage
import com.android.example.ui.settings.Settings
import com.core.common.navigation_constants.ProfilePage
import com.core.feature_api.ProfileApi
import javax.inject.Inject

class InternalProfilePageApi @Inject constructor() : ProfileApi {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun registerGraph(
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            startDestination = ProfilePage.profile,
            route = ProfilePage.nestedRoute
        ) {
            composable(
                "${ProfilePage.profile}/{role}", arguments = listOf(
                    navArgument("role") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val role = backStackEntry.arguments?.getString("role")
                if (role != null) {
                    ProfilePage(
                        navController = navController,
                        role = role
                    )
                }
            }
            composable(
                "${ProfilePage.info}/{role}", arguments = listOf(
                    navArgument("role") { type = NavType.StringType },
                    )
            ) { backStackEntry ->
                val role = backStackEntry.arguments?.getString("role")
                if (role != null) {
                    ProfileInfo(
                        navController = navController,
                        role = role
                    )
                }
            }
            composable(
                "${ProfilePage.settings}/{role}", arguments = listOf(
                    navArgument("role") { type = NavType.StringType },
                    )
            ) { backStackEntry ->
                val role = backStackEntry.arguments?.getString("role")
                if (role != null) {
                    Settings(
                        navController = navController,
                        role = role
                    )
                }
            }
            composable("${ProfilePage.passwordChange}/{role}", arguments = listOf(
                navArgument("role") { type = NavType.StringType },
            )
            ) { backStackEntry ->
                val role = backStackEntry.arguments?.getString("role")
                if (role != null) {
                    PasswordChange(
                        navController = navController,
                        role = role
                    )
                }
            }
            composable("${ProfilePage.notifications}/{role}", arguments = listOf(
                    navArgument("role") { type = NavType.StringType },
            )
            ) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role")
            if (role != null) {
                NotificationsPage(
                    navController = navController,
                    role = role
                )
            }
        }
            composable("${ProfilePage.deleteAccount}/{role}", arguments = listOf(
                navArgument("role") { type = NavType.StringType },
            )
            ) { backStackEntry ->
                val role = backStackEntry.arguments?.getString("role")
                if (role != null) {
                    DeleteAccount(
                        navController = navController,
                        role = role
                    )
                }
            }
        }
    }
}