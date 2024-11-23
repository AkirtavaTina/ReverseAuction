package com.feature.auction.ui.screen.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.core.common.navigation_constants.AuthFeature
import com.feature.auction.ui.R
import com.feature.auction.ui.screen.ui.theme.robotoFamily
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(modifier: Modifier = Modifier, navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FD))
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1F)
        ) { page ->

            when (page) {
                0 -> OnboardingScreen1()
                1 -> OnboardingScreen2()
                2 -> OnboardingScreen3()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Button(
                onClick = { navController.navigate(AuthFeature.signIn) },
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .weight(1F)
                    .padding(start = 20.dp),
                enabled = true,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffF7F9FD),
                    contentColor = Color.Black,
                    disabledContainerColor = Color(0xffF7F9FD),
                    disabledContentColor = Color.Black
                )
            ) {
                Text(
                    text = "Log In",
                    fontSize = 14.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF3263AB)
                )
            }
            Spacer(modifier = Modifier.size(12.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            navController.navigate(AuthFeature.role)
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 15.dp,bottom = 15.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .weight(1F)
                    .padding(end = 20.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFF3263AB),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF3263AB),
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    text = if (pagerState.currentPage < pagerState.pageCount - 1) "Next" else "Sign Up",
                    fontSize = 14.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun OnboardingScreen1() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {

        Box(Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .padding(top = 382.81.dp)
                    .fillMaxWidth()
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(77.dp)
                ) {
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val radiusX = size.width / 2
                    val radiusY = size.height / 2

                    drawOval(
                        color = Color.White,
                        topLeft = Offset(centerX - radiusX, centerY - radiusY),
                        size = Size(radiusX * 2, radiusY * 2),
                        style = Fill
                    )
                }

            }
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 140.dp),
                painter = painterResource(id = R.drawable.auction),
                contentDescription = stringResource(id = R.string.auction_image_description)

            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = (382.81 + 77 / 2).dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 71.dp)
                        .padding(horizontal = 20.dp),
                    text = "Welcome to Sifta",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    fontFamily = robotoFamily,
                    color = Color(0xFF3263AB)
                )

                Text(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 20.dp),
                    text = "Explore top deals and connect with ease. Your marketplace is just a tap away!",
                    fontSize = 14.sp,
                    fontFamily = robotoFamily,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun OnboardingScreen2() {

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {

        Box(Modifier.weight(1f)) {
            Text(
                modifier = Modifier
                    .padding(top = 124.dp)
                    .padding(horizontal = 20.dp),
                text = "How It Works?",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                fontFamily = robotoFamily,
                color = Color(0xFF3263AB)
            )

            Column(
                modifier = Modifier
                    .padding(top = 185.dp)
                    .fillMaxWidth()
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(37.dp)
                ) {
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val radiusX = size.width / 2
                    val radiusY = size.height / 2

                    drawOval(
                        color = Color.White,
                        topLeft = Offset(centerX - radiusX, centerY - radiusY),
                        size = Size(radiusX * 2, radiusY * 2),
                        style = Fill
                    )
                }

            }

            Column(
                modifier = Modifier
                    .padding(top = (185 + 37 / 2).dp)
                    .background(Color.White)
                    .fillMaxWidth()
                    .fillMaxHeight(),
            ) {
                Column(modifier = Modifier.padding(top = 24.dp)) {
                    Card(
                        R.drawable.post,
                        "Post Your Need",
                        "Buyers specify their requirements and budget."
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    Card(
                        R.drawable.offer,
                        "Get Offers",
                        "Sellers respond with their best prices and product details."
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    Card(
                        R.drawable.compare,
                        "Compare & Choose",
                        "Buyers review offers and select the best one."
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    Card(
                        R.drawable.secure,
                        "Secure & Anonymous",
                        "The platform manages communication and delivery."
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    Card(
                        R.drawable.rate,
                        "Rate & Review",
                        "Buyers leave feedback to help others."
                    )
                }

            }
        }
    }
}


@Composable
fun OnboardingScreen3() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {

        Box(Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .padding(top = 292.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                ) {
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val radiusX = size.width / 2
                    val radiusY = size.height / 2

                    drawOval(
                        color = Color.White,
                        topLeft = Offset(centerX - radiusX, centerY - radiusY),
                        size = Size(radiusX * 2, radiusY * 2),
                        style = Fill
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = (292 + 58 / 2).dp)
                    .fillMaxSize()
                    .background(Color.White),
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 100.dp)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Why Sifta?",
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        fontFamily = robotoFamily,
                        color = Color(0xFF3263AB)
                    )
                    Card2(
                        R.drawable.tick,
                        R.string.post_image_description,
                        "Get the best offers and control your choices."
                    )
                    Card2(
                        R.drawable.tick,
                        R.string.post_image_description,
                        "Compete to showcase your best deals and attract more business."
                    )
                    Card2(
                        R.drawable.tick,
                        R.string.post_image_description,
                        "Secure and anonymous interactions for all."
                    )
                    Box(
                        Modifier
                            .weight(1F)
                            .background(Color.White))
                }
            }
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 140.dp),
                painter = painterResource(id = R.drawable.man_looking_on_mobile_while_no_found_result),
                contentDescription = stringResource(id = R.string.auction_image_description)

            )
        }
    }
}

@Composable
fun Card(id: Int, label: String, description: String) {

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {

        Icon(
            painter = painterResource(id = id),
            contentDescription = null,
            tint = Color(0xFF3263AB),
            modifier = Modifier.padding(end = 16.dp)
        )
        Column {
            Text(
                text = label,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                fontFamily = robotoFamily
            )
            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                color = Color(0xFF898989)
            )
        }

    }
}

@Composable
fun Card2(id: Int, stringId: Int, description: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(end = 8.dp),
            painter = painterResource(id = id),
            contentDescription = stringResource(id = stringId)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = description,
            fontSize = 14.sp,
            fontFamily = robotoFamily,
            color = Color(0xFF898989)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
//    OnboardingScreen()
    OnboardingScreen3()
}