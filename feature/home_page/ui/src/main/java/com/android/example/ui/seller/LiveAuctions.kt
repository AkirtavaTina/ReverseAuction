package com.android.example.ui.seller

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.example.ui.R
import com.android.example.ui.theme.robotoFamily


@Composable
fun LiveAuctionPage(role: String) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .verticalScroll(state = rememberScrollState(), enabled = true)
    ) {
        FilterByCategory()
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Header("Premium Auction")
            Text(
                text = "See All",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF000000),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
        Row(modifier = Modifier.horizontalScroll(state = rememberScrollState(), enabled = true)) {
            BidItem()
            Spacer(modifier = Modifier.width(12.dp))
            BidItem()
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Header("Auctions Closes Soon")
            Text(
                text = "See All",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF000000),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
        Row(modifier = Modifier.horizontalScroll(state = rememberScrollState(), enabled = true)) {
            MyCard2(role)
            Spacer(modifier = Modifier.width(12.dp))
            MyCard2(role)
            Spacer(modifier = Modifier.width(12.dp))
            MyCard2(role)
        }
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
fun FilterByCategory() {
    val popularItemsNames by remember {
        mutableStateOf(
            listOf(
                "Watches", "Vehicles", "Training Equipment"
            )
        )
    }
    Spacer(modifier = Modifier.size(16.dp))
    Text(
        text = "Filter by Category",
        fontSize = 16.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        CardItem(
            "Most Popular", R.drawable.headphones, {}, Modifier.weight(0.44f), 156, 154, 16, 5.14,
        )

        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = Modifier.weight(0.44f)) {
            Row {
                CardItem(
                    popularItemsNames[0],
                    R.drawable.image_29,
                    {},
                    Modifier.weight(0.44f),
                    72,
                    71,
                    10,
                    12.0,
                )
                Spacer(modifier = Modifier.size(12.dp))
                CardItem(
                    popularItemsNames[1],
                    R.drawable.car,
                    {},
                    Modifier.weight(0.44f),
                    72,
                    71,
                    10,
                    12.0,

                    )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row() {
                CardItem(
                    popularItemsNames[2],
                    R.drawable.image_28,
                    {},
                    Modifier.weight(0.44f),
                    72,
                    71,
                    10,
                    12.0,

                    )
                Spacer(modifier = Modifier.size(12.dp))
                CardItem("See All", -1, {}, Modifier.weight(0.44f), 72, 71, 10, 12.0)
            }
        }
    }
}

@Composable
fun BidItem() {
    Column(
        modifier = Modifier
            .background(Color(0xFFFBFCFF))
            .padding(horizontal = 10.dp)
            .padding(vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.car),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(vertical = 5.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.width(149.dp)) {
                Text(
                    text = "Vintage car for Collection",
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF000000),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.diamond),
                        contentDescription = null,
                        modifier = Modifier
                            .size(12.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = "Premium Auction",
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(217.dp)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Current Bid",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFF555555),
                textAlign = TextAlign.Center
            )
            Text(
                text = "$ 750.00",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color(0xFF3263AB),
                textAlign = TextAlign.Center
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(217.dp)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Max Budget",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFF555555),
                textAlign = TextAlign.Center
            )
            Text(
                text = "$ 850.00",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        }
        FilledTonalButton(
            onClick = {}, colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = Color(0xFF3263AB)
            ), shape = RoundedCornerShape(8.dp), modifier = Modifier
                .width(217.dp)
                .height(32.dp)
        ) {
            Text(
                text = "Bid now",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center
            )
        }
    }

}

@Composable
fun myCardItem(
    text: String,
    height: Dp,
    width: Dp,
    onClick: () -> Unit,
    fontSize: Int,
    imageId: Int,
    imageSize: Dp
) {
    Column(
        Modifier
            .height(height)
            .width(width)
            .background(Color(0xFFFBFCFF), RoundedCornerShape(6.dp))
            .border(0.5.dp, Color(0xFFE8EFF9), RoundedCornerShape(6.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageId != -1) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier
                    .size(imageSize)
            )
            Spacer(modifier = Modifier.size(6.dp))
        }
        Text(
            text = text,
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = fontSize.sp,
            color = Color(0xFF000000),
        )
    }
}

@Composable
fun CardItem(
    text: String,
    id: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Int,
    width: Int,
    fontSize: Int,
    imageScale: Double,

    ) {
    var cardWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Card(
        onClick = { onClick() },
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                val widthInPx = coordinates.size.width
                cardWidth = with(density) { widthInPx.toDp() }
            }
            .border(0.5.dp, Color(0xFFE8EFF9), RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBFCFF))
    ) {
        Column(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .height(cardWidth * ((height / width))),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (id >= 0) {
                Image(
                    painter = painterResource(id = id),
                    contentDescription = null,
                    modifier = Modifier
                        .size((screenWidth / imageScale.dp).dp)
                )
            }
            Spacer(Modifier.size(8.dp))
            Text(
                text = text,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                fontSize = fontSize.sp,
                color = Color(0xFF000000),
                style = TextStyle()
            )
        }
    }
}

@Composable
fun Header(text: String) {
    Text(
        text = text,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        color = Color(0xFF000000),
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun MyCard2(role: String) {
    Column(
        modifier = Modifier
            .background(Color(0xFFFBFCFF))
            .background(Color(0xFFFBFCFF), shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFF8F8F8), shape = RoundedCornerShape(12.dp))
            .width(154.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_29),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "Vintage car for\nCollection",
            fontFamily = robotoFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.alarm), contentDescription = null
            )
            Text(
                text = " 5D:12H:2M",
                fontFamily = robotoFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF373737)
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "Max Budget",
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = robotoFamily,
                color = Color(0xFF555555)
            )
            Text(
                "$850.00",
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = robotoFamily,
                color = Color(0xFF3263AB)
            )
        }
        Spacer(Modifier.size(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "Lowest Bid",
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = robotoFamily,
                color = Color(0xFF555555)
            )
            Text(
                "$750.00",
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = robotoFamily,
                color = Color(0xFF000000)
            )
        }

        Spacer(modifier = Modifier.size(24.dp))
        FilledTonalButton(
            onClick = {},
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = Color(0xFF3263AB)
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        ) {
            Text(
                text = if (role == "Seller") "Bid now" else "View More",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LiveAuctionPreview() {
//    LiveAuctionPage()
//}
