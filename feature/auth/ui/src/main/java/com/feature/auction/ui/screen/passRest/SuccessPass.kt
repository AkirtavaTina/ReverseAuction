package com.feature.auction.ui.screen.passRest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.core.common.navigation_constants.AuthFeature
import com.feature.auction.ui.R
import com.feature.auction.ui.screen.ui.theme.ReverseAuctionTheme
import com.feature.auction.ui.screen.ui.theme.robotoFamily


@Composable
fun SuccessPassPage(modifier: Modifier = Modifier, navController: NavController) {

    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(top = 155.dp + 48.dp)
            .padding(horizontal = 45.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Icon(
            painter = painterResource(id = R.drawable.check),
            "check",
            tint = Color.White,
            modifier = Modifier
                .background(
                    color = Color(
                        0xFF8BBF56
                    ),
                    shape = RoundedCornerShape(100)
                )
                .size(36.dp),
        )


        Text(
            text = "Your password has been reset successfully",
            modifier = Modifier.padding(top = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontFamily = robotoFamily
        )

        Button(
            onClick = {

                navController.navigate(AuthFeature.signIn)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonColors(
                containerColor = Color(0xFF3263AB),
                contentColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFF94A9C8),
                disabledContentColor = Color(0xFFFFFFFF)
            )
        ) {
            Text(
                text = "Log In",
                color = Color(0xFFFFFFFF),
                fontSize = 14.sp,
                fontFamily = robotoFamily
            )
        }
    }


}


@Preview(showBackground = true)
@Composable
fun SuccessPassPreview() {
    ReverseAuctionTheme {
//        SuccessPassPage()
    }
}