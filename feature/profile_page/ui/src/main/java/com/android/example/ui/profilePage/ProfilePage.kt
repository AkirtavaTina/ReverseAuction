package com.android.example.ui.profilePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.android.example.ui.R
import com.android.example.ui.profileInfo.getStatusBarHeight
import com.android.example.ui.theme.robotoFamily
import com.core.common.navigation_constants.HomePage
import com.core.common.navigation_constants.ProfilePage


@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfilePageViewModel = hiltViewModel(),
    role: String
) {
    val profilePageState by viewModel.state.collectAsStateWithLifecycle()

    val statusBarHeight = with(LocalDensity.current) {
        getStatusBarHeight().toDp()
    }

    Column(
        modifier = modifier
            .background(Color.White)
//            .padding(top = statusBarHeight)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
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
                contentDescription = "forward icon",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.navigate("${HomePage.homePage}/${role}") {
                            popUpTo(ProfilePage.profile) { inclusive = true }
                        }
                    }
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Profile", fontFamily = robotoFamily, fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
        }

        Column(
            modifier = modifier
                .weight(1F)
                .fillMaxHeight()
                .padding(top = 8.dp)
                .background(Color.White)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier.padding(bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_default_pic),
                    contentDescription = "default profile picture",
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = profilePageState.profileName,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 12.dp)
                        .width(65.dp)
                        .border(1.dp, Color(0xFFE8EFF9), RoundedCornerShape(8.dp))
                        .background(Color(0xFFFBFCFF), RoundedCornerShape(8.dp))
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.yellow_star),
                        contentDescription = "yellow star",
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(16.dp)
                    )
                    Text(
                        text = "5.0",
                        fontSize = 12.sp,
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        fontFamily = robotoFamily,
                    )
                }

                Row(
                    modifier = Modifier
                        .height(32.dp)
                        .border(1.dp, Color(0xFFE8EFF9), RoundedCornerShape(8.dp))
                        .clickable { viewModel.changeBooleanField(!profilePageState.verified) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = if (profilePageState.verified) R.drawable.new_releases else R.drawable.add_circle),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 4.5.dp)
                            .width(18.dp)
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 16.dp),
                        text = if (profilePageState.verified) "Verified" else "Get verified",
                        textAlign = TextAlign.Center,
                        fontFamily = robotoFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (profilePageState.verified) Color(0xFF34A853) else Color(
                            0xFF000000
                        )
                    )
                }
            }

            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(
                        1.dp,
                        Color(0xFFF3F3F3),
                        RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailsItem(R.drawable.account_circle, "Profile Info") {
                        navController.navigate(
                            "${ProfilePage.info}/${role}"
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    DetailsItem(R.drawable.settings, "Settings") {
                        navController.navigate(
                           "${ProfilePage.settings}/${role}"
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    DetailsItem(R.drawable.reviews, "Review") {}
                    Spacer(modifier = Modifier.height(15.dp))
                    DetailsItem(R.drawable.leaderboard, "Leaderboard") {}
                    Spacer(modifier = Modifier.height(15.dp))
                    DetailsItem(R.drawable.history, "History") {}
                    Spacer(modifier = Modifier.height(15.dp))
                    DetailsItem(R.drawable.info, "About Us") {}
                    Spacer(modifier = Modifier.height(15.dp))
                    DetailsItem(R.drawable.support_agent, "Support") {}

                    Spacer(modifier = Modifier.weight(1F))

                    FilledTonalButton(
                        onClick = {
                            viewModel.logout(navController)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 17.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(Color(0xFFF24646)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Log Out",
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.White,
                                modifier = Modifier.padding(end = 10.dp)
                            )

                            Image(
                                painter = painterResource(id = R.drawable.move_item),
                                contentDescription = "log out",
                                modifier = Modifier
                                    .size(24.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DetailsItem(id: Int, text: String, onClick: () -> Unit) {
    Column(modifier = Modifier
        .padding(horizontal = 20.dp)
        .clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = id),
                        contentDescription = null,
                        tint = Color(0xFF3263AB)
                    )
                }
                Text(
                    text = text,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 18.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.chevron_forward),
                contentDescription = "forward icon",
                modifier = Modifier
                    .size(24.dp),
                colorFilter = ColorFilter.tint(Color(0xFF3263AB))
            )
        }
    }

    HorizontalDivider(
        color = Color(0xFFF3F3F3), modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )

}


