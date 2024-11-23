package com.feature.auction.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.core.common.navigation_constants.AuthFeature
import com.core.feature_api.FeatureApi
import com.feature.auction.ui.screen.onboarding.OnboardingScreen
import com.feature.auction.ui.screen.passRest.SuccessPassPage
import com.feature.auction.ui.screen.passRest.forgotPass.ForgotPasswordPage
import com.feature.auction.ui.screen.passRest.resetPassword.ResetCodePage
import com.feature.auction.ui.screen.passRest.resetPassword.ResetPasswordPage
import com.feature.auction.ui.screen.passRest.verification.VerificationPage
import com.feature.auction.ui.screen.signin.SignInPage
import com.feature.auction.ui.screen.signup.RolePage
import com.feature.auction.ui.screen.signup.SignUpPage
import javax.inject.Inject

internal class InternalAuthFeatureApi @Inject constructor() : FeatureApi {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun registerGraph(
        navController: androidx.navigation.NavHostController,
        navGraphBuilder: androidx.navigation.NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            startDestination = AuthFeature.onboarding,
            route = AuthFeature.nestedRoute
        ) {
            composable(AuthFeature.onboarding) {
                OnboardingScreen(navController = navController)
            }
            composable(AuthFeature.role) {
                RolePage(navController = navController)
            }
            composable(
                route = "${AuthFeature.signUp}/{role}",
                arguments = listOf(navArgument("role") { type = NavType.StringType })
            ) { backStackEntry ->
                val role = backStackEntry.arguments?.getString("role")
                SignUpPage(navController = navController, role = role!!)
            }
            composable(AuthFeature.signIn) {
                SignInPage(navController = navController)
            }
            composable(AuthFeature.forgotPass) {
                ForgotPasswordPage(navController = navController)
            }
            composable(
                route = "${AuthFeature.verification}/{userId}/{email}/{type}",
                arguments = listOf(
                    navArgument("userId") { type = NavType.StringType },
                    navArgument("email") { type = NavType.StringType },
                    navArgument("type") { type = NavType.StringType },
                )) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId")
                val email = backStackEntry.arguments?.getString("email")
                val type = backStackEntry.arguments?.getString("type")
                    VerificationPage(
                        userId = userId,
                        email = email,
                        type = type,
                        navController = navController
                    )
                }


            composable(AuthFeature.success) {
                SuccessPassPage(navController = navController)
            }

            composable(route = "${AuthFeature.reset}/{token}/{uidb}",
                arguments = listOf(
                    navArgument("token") { type = NavType.StringType },
                    navArgument("uidb") { type = NavType.StringType },

                )) { backStackEntry ->
                val token = backStackEntry.arguments?.getString("token")
                val uidb = backStackEntry.arguments?.getString("uidb")
                ResetPasswordPage(token = token, uidb = uidb, navController = navController)
            }

            composable(route = "${AuthFeature.resend}/{email}",
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType }
                )) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email")
                ResetCodePage(email = email)
            }
        }
    }
}