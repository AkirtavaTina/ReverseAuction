package com.android.example.ui.buyer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.example.ui.R
import com.android.example.ui.seller.FilterByCategory
import com.android.example.ui.seller.Header
import com.android.example.ui.seller.MyCard2
import com.android.example.ui.theme.robotoFamily
import com.core.common.navigation_constants.HomePage

@Composable
fun BuyerLiveAuctions(
    modifier: Modifier = Modifier, navController: NavController, role:String
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.size(12.dp))
        AddNewAuctionCard(navController)
        Spacer(Modifier.size(24.dp))
        MyAuctions()
        Spacer(modifier = Modifier.size(8.dp))
        FilterByCategory()
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Header("All")
        }
        Row(modifier = Modifier.horizontalScroll(state = rememberScrollState(), enabled = true)) {
            MyCard2(role)
            Spacer(modifier = Modifier.width(12.dp))
            MyCard2(role)
            Spacer(modifier = Modifier.width(12.dp))
            MyCard2(role)
        }
        Spacer(modifier = Modifier.size(12.dp))
        Row(modifier = Modifier.horizontalScroll(state = rememberScrollState(), enabled = true)) {
            MyCard2(role)
            Spacer(modifier = Modifier.width(12.dp))
            MyCard2(role)
            Spacer(modifier = Modifier.width(12.dp))
            MyCard2(role)
        }
        Spacer(modifier = Modifier.size(12.dp))
        Row(modifier = Modifier.horizontalScroll(state = rememberScrollState(), enabled = true)) {
            MyCard2(role)
            Spacer(modifier = Modifier.width(12.dp))
            MyCard2(role)
            Spacer(modifier = Modifier.width(12.dp))
            MyCard2(role)
        }
        Spacer(modifier = Modifier.size(56.dp))
    }
}


@Composable
fun AddNewAuctionCard(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
        ) {
            val dashWidth = 10.dp.toPx()
            val dashGap = 10.dp.toPx()
            drawRoundRect(
                color = Color(0xFFE8EFF9),
                style = Stroke(
                    width = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx())
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3263AB))
                    .clickable { navController.navigate(HomePage.addAction) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.data_saver_on),
                    contentDescription = "Add Auction",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Add New Auction",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF3263AB),
                textAlign = TextAlign.Center,
                fontFamily = robotoFamily
            )
        }
    }
}

@Composable
fun MyAuctions() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFFFBFCFF), RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFE8EFF9), RoundedCornerShape(12.dp))
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
                .padding(top = 12.dp),
            text = "My Auctions",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = robotoFamily
        )
        Spacer(modifier = Modifier.size(19.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_box),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(Modifier.size(16.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "No Auctions Yet !",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = robotoFamily,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(12.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "You haven't started any auctions yet. " +
                        "Create a new one and let sellers offer their best deals!",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = robotoFamily,
                textAlign = TextAlign.Center,
                color = Color(0xFF9E9E9E),
            )
            Spacer(Modifier.size(26.dp))
        }
    }
}

//@Composable
//@Preview
//fun BuyerPreview() {
//    BuyerLiveAuctions()
//}