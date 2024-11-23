package com.android.example.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.example.ui.profileInfo.getStatusBarHeight
import com.android.example.ui.theme.robotoFamily
import com.core.common.navigation_constants.ProfilePage

@Composable
fun NotificationsPage(
    modifier: Modifier = Modifier, navController: NavController,
    role:String
) {
    val statusBarHeight = with(LocalDensity.current) {
        getStatusBarHeight().toDp()
    }
    Column(
        modifier = modifier
            .background(Color.White)
//            .padding(top = statusBarHeight)
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.chevron_backward),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.navigate("${ProfilePage.settings}/${role}")
                    })
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Notifications",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.throwing_papers),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
            )
            Spacer(Modifier.size(24.dp))
            Text(
                text = "Nothing to See Yet",
                color = Color(0xFF3263AB),
                fontSize = 16.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.size(12.dp))
            Text(
                text = "Your notifications are empty. Once you start \n engaging with auctions, you'll see updates \n here!",
                color = Color(0xFF585858),
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}

//@Composable
//@Preview
//fun NotificationsPreview() {
//    NotificationsPage(Modifier)
//}