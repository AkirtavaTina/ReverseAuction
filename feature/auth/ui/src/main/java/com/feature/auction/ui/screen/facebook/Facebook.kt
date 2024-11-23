package com.feature.auction.ui.screen.facebook

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.feature.auction.ui.R
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


@Composable
fun FacebookSignUpButton(viewModel: FacebookViewModel = hiltViewModel(), navController: NavController, role: String){
    val cont = LocalContext.current
    printHashKey(LocalContext.current)
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(Color(0xFFF7F9FD), shape = RoundedCornerShape(16.dp))
            .clickable {
                getFacebookToken(cont, viewModel, navController, role)
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.facebook),
            contentDescription = "facebook",
            modifier = Modifier
                .size(16.dp)
        )
    }
}

@Composable
fun FacebookSignInButton(viewModel: FacebookViewModel = hiltViewModel(), navController: NavController){
    val cont = LocalContext.current
    printHashKey(LocalContext.current)
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(Color(0xFFF7F9FD), shape = RoundedCornerShape(16.dp))
            .clickable {
                getFacebookToken(cont, viewModel, navController, "")
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.facebook),
            contentDescription = "facebook",
            modifier = Modifier
                .size(16.dp)

        )
    }
}





fun printHashKey(context: Context) {
    try {
        val info: PackageInfo = context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_SIGNATURES
        )
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            val hashKey = String(Base64.encode(md.digest(), 0))
            Log.d("hashKey", "Hash key: $hashKey")
        }

    } catch (e: NoSuchAlgorithmException) {
        e.localizedMessage?.let { Log.d("Error", it) }
    } catch (e: Exception) {
        e.localizedMessage?.let { Log.d("Exception", it) }
    }
}


private fun getFacebookToken(context: Context, viewModel: FacebookViewModel, navController: NavController, role: String) {
    val callbackManager = CallbackManager.Factory.create()
    val loginManager = LoginManager.getInstance()
    loginManager.setLoginBehavior(LoginBehavior.DIALOG_ONLY)

    loginManager.logInWithReadPermissions(
        context as ActivityResultRegistryOwner,
        callbackManager,
        listOf("email", "public_profile")
    )
    loginManager.registerCallback(
        callbackManager,
        object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                Log.d("socials", "Login onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("socials", "Login onError ${error.message}")
            }

            override fun onSuccess(result: LoginResult) {
                if(role == "")  viewModel.signIn(navController = navController, result.accessToken.token)
                else viewModel.signUp(navController = navController, result.accessToken.token, role)
            }
        })
}