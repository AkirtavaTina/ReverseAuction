package com.android.example.reverseauction

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.example.reverseauction.navigation.AppNavGraph
import com.android.example.reverseauction.navigation.NavigationProvider
import com.android.example.reverseauction.ui.theme.ReverseAuctionTheme
import com.core.network.di.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationProvider: NavigationProvider

    private lateinit var networkStatus: NetworkStatus

    @RequiresApi(UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        networkStatus = NetworkStatus(this)

        if (!networkStatus.isConnected()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }

        networkStatus.registerNetworkCallback(
            onLost = {
                runOnUiThread {
                    Toast.makeText(this, "Internet connection lost", Toast.LENGTH_SHORT).show()
                }
            }
        )

        setContent {
            ReverseAuctionTheme {
                val navController = rememberNavController()
                UpdateStatusBarColor(this)
                App(navHostController = navController, navigationProvider)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App(navHostController: NavHostController, navigationProvider: NavigationProvider){
    Surface {
        AppNavGraph(navController = navHostController, navigationProvider = navigationProvider)
    }
}

@Composable
fun UpdateStatusBarColor(context: Context) {
    val statusBarColor = Color.White
    val navigationBarColor = Color.White

    SideEffect {
        val window = (context as Activity).window
        window.statusBarColor = statusBarColor.toArgb()
        window.navigationBarColor = navigationBarColor.toArgb()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = true
    }
}