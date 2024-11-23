package com.android.example.ui.seller

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.example.ui.R
import com.android.example.ui.theme.robotoFamily
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardPage(
    modifier: Modifier = Modifier,
    role:String
//                  navController: NavController,
//                  userId: String
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            val image: Painter = painterResource(id = R.drawable.calendar_month)
            Icon(
                painter = image,
                contentDescription = null,
                tint = Color(0xFF939393)
            )

            Spacer(modifier = Modifier.size(6.dp))
            Surface {
                val currentDate = LocalDate.now()
                val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                Text(
                    modifier = Modifier.background(Color.White),
                    text = formattedDate,
                    color = Color(0xFF939393),
                    fontSize = 11.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyCard(
                    text = "Total Income",
                    color = Color(0xFFEAE7FF),
                    color2 = Color(0xFF998DF9),
                    background = Color(0xFFF4F2FF),
                    income = "\$ 0.00",
                    modifier = Modifier.weight(1f),
                    id = R.drawable.mintmark

                )
                Spacer(modifier = Modifier.size(8.dp))

                MyCard(
                    text = "Sold Products",
                    color = Color(0xFFFFE3CC),
                    color2 = Color(0xFFF8A942),
                    background = Color(0xFFFFF5E8),
                    income = "12",
                    modifier = Modifier.weight(1f),
                    id = R.drawable.payments
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))

        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyCard(
                    text = "Active Bids",
                    color = Color(0xFFE3F2FF),
                    color2 = Color(0xFF62B5FA),
                    background = Color(0xFFF3FAFF),

                    income = "12",
                    modifier = Modifier.weight(1f),
                    id = R.drawable.hand_gesture1
                )
                Spacer(modifier = Modifier.size(8.dp))
                MyCard(
                    text = "Rejected Bids",
                    color = Color(0xFFFDCBD6),
                    color2 = Color(0xFFEB3260),
                    background = Color(0xFFFEF0F3),
                    income = "5",
                    modifier = Modifier.weight(1f),
                    id = R.drawable.do_not_disturb_off
                )
            }
        }
        if(role == "Seller"){
        Spacer(modifier = Modifier.size(24.dp))

            Column(
                modifier = Modifier
                    .background(Color(0xFFFBFCFF))
                    .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 24.dp)

            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your Bids History",
                        fontFamily = robotoFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )

                    Text(
                        text = "See All",
                        fontFamily = robotoFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF777777)
                    )
                }
                Spacer(modifier = Modifier.size(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Auction ID",
                        fontFamily = robotoFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF777777)
                    )
                    Text(
                        text = "Bid",
                        fontFamily = robotoFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF777777)
                    )
                    Text(
                        text = "Time",
                        fontFamily = robotoFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF777777)
                    )
                    Text(
                        text = "Date",
                        fontFamily = robotoFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF777777)
                    )
                    Text(
                        text = "Action",
                        fontFamily = robotoFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF777777)
                    )
                }

                BidsCard("#111111", "$ 150.00", "16:30", "04.09.2024", Color(0xFFE53935))
                BidsCard("#111111", "$ 150.00", "16:30", "04.09.2024", Color(0xFF4CAF50))
                BidsCard("#111111", "$ 150.00", "16:30", "04.09.2024", Color(0xFFFFB300))
                BidsCard("#111111", "$ 150.00", "16:30", "04.09.2024", Color(0xFF4CAF50))
                BidsCard("#111111", "$ 150.00", "16:30", "04.09.2024", Color(0xFFFFB300))
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    BidsHistory(color = Color(0xFFE53935), text = "Rejected")
                    Spacer(modifier = Modifier.size(12.dp))
                    BidsHistory(color = Color(0xFFFFB300), text = "Pending")
                    Spacer(modifier = Modifier.size(12.dp))
                    BidsHistory(color = Color(0xFF4CAF50), text = "Accepted")
                }
            }
        }
        Sales()
        Column(modifier = Modifier.padding(vertical = 24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "For You",
                    fontFamily = robotoFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text("See all")
            }
            Spacer(modifier = Modifier.size(12.dp))
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                MyCard()
                MyCard()
                MyCard()
            }

        }
        Popularity()
        LeaderBoard()
    }
}

@Composable
fun BidsHistory(color: Color, text: String) {
    Row(
        modifier = Modifier.padding(horizontal = 7.5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier.size(10.dp)
        ) {
            drawCircle(
                color = color,
                radius = size.minDimension / 2,
                center = Offset(size.width / 2, size.height / 2),
                style = Fill
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            fontFamily = robotoFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF676767)
        )
    }
}

@Composable
fun BidsCard(auctionId: String, bid: String, time: String, date: String, color: Color) {
    Spacer(modifier = Modifier.size(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
            .border(
                1.dp,
                color = Color(0x14676767),
                RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
            )
            .background(Color.White)
            .drawBehind {
                val strokeWidth = 6.dp.toPx()
                val height = size.height
                drawLine(
                    color = color,
                    start = Offset(0f, 0f),
                    end = Offset(0f, height),
                    strokeWidth = strokeWidth
                )
            }
            .padding(horizontal = 12.dp, vertical = 17.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = auctionId,
            fontFamily = robotoFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF676767)
        )
        Text(
            text = bid,
            fontFamily = robotoFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF676767)
        )
        Text(
            text = time,
            fontFamily = robotoFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF676767)
        )
        Text(
            text = date,
            fontFamily = robotoFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF676767)
        )
        IconButton(
            modifier = Modifier.size(16.dp),
            onClick = { /* Do something */ }) {
            val image: Painter = painterResource(id = R.drawable.open_in_new)
            Icon(
                painter = image,
                contentDescription = null,
            )
        }

    }
}

@Composable
fun MyCard(
    text: String,
    color: Color,
    color2: Color,
    background: Color,
    id: Int,
    income: String,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .height(96.dp)
            .border(0.5.dp, Color(0xFFE8EFF9), RoundedCornerShape(10.dp))
            .background(background, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center,

        ) {
        Row {
            Icon(
                painter = painterResource(id),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .background(color, RoundedCornerShape(42.dp))
                    .wrapContentSize(align = Alignment.Center),
                tint = color2
            )
            Spacer(modifier = Modifier.size(4.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = text,
                    fontFamily = robotoFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = income, color = color2, fontFamily = robotoFamily, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun MyButton(icon: Int, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.Unspecified,
        border = BorderStroke(1.dp, Color(0xFFEBEBEB))
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clickable(
                    indication = null, // Remove ripple effect
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            val image: Painter = painterResource(id = icon)
            Icon(
                painter = image,
                contentDescription = null,
                tint = Color(0xFF3263AB),
                modifier = Modifier.size(25.dp)
            )
        }
    }
}


@Composable
fun Popularity() {
    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }
    val chosenItemPosition = remember {
        mutableIntStateOf(0)
    }
    val category = listOf("Category", "Category1", "Category2")

    val selected = remember {
        mutableStateOf("This Week")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        colors = CardDefaults.cardColors(Color(0xFFFBFCFF)),
        border = BorderStroke(1.dp, color = Color(0xFFEEEEEE))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Product Popularity", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                Spacer(modifier = Modifier.padding(end = 22.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(6.dp))
                        .clickable(
                            indication = null, // Remove ripple effect
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            isDropDownExpanded.value = true
                        }

                ) {
                    Text(
                        text = category[chosenItemPosition.intValue],
                        fontSize = 12.sp,
                        modifier = Modifier.padding(
                            start = 22.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        )
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "drop down icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(4.dp)
                    )

                    DropdownMenu(
                        modifier = Modifier.background(Color.White),
                        expanded = isDropDownExpanded.value,
                        onDismissRequest = {
                            isDropDownExpanded.value = false
                        }) {
                        category.forEachIndexed { index, category ->
                            DropdownMenuItem(text = {
                                Text(text = category)
                            },
                                onClick = {
                                    isDropDownExpanded.value = false
                                    chosenItemPosition.intValue = index
                                })
                        }
                    }
                }
            }


            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .padding(vertical = 6.dp)
                        .padding(start = 8.dp)
                        .background(
                            if (selected.value == "Today") Color(0xFFF7F9FD) else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            0.5.dp,
                            if (selected.value == "Today") Color(0xFFE8EFF9) else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )
                        .clickable(
                            indication = null, // Remove ripple effect
                            interactionSource = remember { MutableInteractionSource() }
                        ) { selected.value = "Today" },
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "Today",
                        modifier = Modifier.padding(vertical = 6.dp),
                        fontFamily = robotoFamily,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .padding(vertical = 6.dp)
                        .padding(start = 8.dp)
                        .background(
                            if (selected.value == "This Week") Color(0xFFF7F9FD) else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            0.5.dp,
                            if (selected.value == "This Week") Color(0xFFE8EFF9) else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )
                        .clickable(
                            indication = null, // Remove ripple effect
                            interactionSource = remember { MutableInteractionSource() }
                        ) { selected.value = "This Week" },
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "This Week",
                        modifier = Modifier.padding(vertical = 6.dp),
                        fontFamily = robotoFamily,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .padding(vertical = 6.dp)
                        .padding(start = 8.dp)
                        .background(
                            if (selected.value == "This Month") Color(0xFFF7F9FD) else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            0.5.dp,
                            if (selected.value == "This Month") Color(0xFFE8EFF9) else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )
                        .clickable(
                            indication = null, // Remove ripple effect
                            interactionSource = remember { MutableInteractionSource() }
                        ) { selected.value = "This Month" },
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "This Month",
                        modifier = Modifier.padding(vertical = 6.dp),
                        fontFamily = robotoFamily,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                }
            }

            Text(
                text = "From 1-8 Sep, 2024",
                fontWeight = FontWeight.Light,
                fontFamily = robotoFamily,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Canvas(
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .width(288.dp)
                        .height(160.dp),
                ) {
                    val centerX = size.width / 2
                    val centerY = size.height / 2

                    val radius = 62.dp.toPx()
                    val strokeWidth = 25.dp.toPx()

                    val totalAngle = 360f
                    val firstAngle = 115 / 320f * totalAngle
                    val secondAngle = 115 / 320f * totalAngle
                    val thirdAngle = 90 / 320f * totalAngle

                    drawArc(
                        color = Color(0xFFFE8274),
                        startAngle = -90f,
                        sweepAngle = firstAngle,
                        useCenter = false,
                        style = Stroke(strokeWidth, cap = StrokeCap.Butt),
                        size = Size(2 * radius, 2 * radius),
                        topLeft = Offset(centerX - radius, centerY - radius)
                    )

                    drawArc(
                        color = Color(0xFFF4BE37),
                        startAngle = -90f + firstAngle,
                        sweepAngle = secondAngle,
                        useCenter = false,
                        style = Stroke(strokeWidth, cap = StrokeCap.Butt),
                        size = Size(2 * radius, 2 * radius),
                        topLeft = Offset(centerX - radius, centerY - radius)
                    )

                    drawArc(
                        color = Color(0xFFA5A2FB),
                        startAngle = -90f + firstAngle + secondAngle,
                        sweepAngle = thirdAngle,
                        useCenter = false,
                        style = Stroke(strokeWidth, cap = StrokeCap.Butt),
                        size = Size(2 * radius, 2 * radius),
                        topLeft = Offset(centerX - radius, centerY - radius)
                    )
                }
            }


            Spacer(modifier = Modifier.padding(top = 32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Canvas(modifier = Modifier.size(10.dp), onDraw = {
                        drawCircle(color = Color(0xFFFE8274))
                    })
                    Text(
                        text = "Strawberry",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Text(
                    text = "115 Sales",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = robotoFamily
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Canvas(modifier = Modifier.size(10.dp), onDraw = {
                        drawCircle(color = Color(0xFFF4BE37))
                    })
                    Text(
                        text = "Potato",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = robotoFamily,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Text(text = "115 Sales", fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Canvas(modifier = Modifier.size(10.dp), onDraw = {
                        drawCircle(color = Color(0xFFA5A2FB))
                    })
                    Text(
                        text = "Tomato",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = robotoFamily,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Text(text = "90 Sales", fontSize = 12.sp, fontWeight = FontWeight.Medium, fontFamily = robotoFamily)
            }
        }
    }
}

@Composable
fun Sales() {
    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val chosenItemPosition = remember {
        mutableIntStateOf(0)
    }

    val year = listOf("This Year", "Last Year", "Next Year")

    val barChartInputs = mapOf(
        "Jan" to 700,
        "Feb" to 1300,
        "Mar" to 1700,
        "Apr" to 1600,
        "May" to 1400,
        "Jun" to 1900,
        "Jul" to 1700,
        "Aug" to 2500,
        "Sep" to 2100,
        "Oct" to 400,
        "Nov" to 2000,
        "Dec" to 2300
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        colors = CardDefaults.cardColors(Color(0xFFFBFCFF)),
        border = BorderStroke(1.dp, color = Color(0xFFEEEEEE))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sales", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(
                        indication = null, // Remove ripple effect
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        isDropDownExpanded.value = true
                    }
                ) {
                    Text(
                        text = year[chosenItemPosition.intValue],
                        fontSize = 14.sp,
                        modifier = Modifier.padding(
                            start = 22.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.material_symbols_arrow_drop_down_circle_rounded),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(4.dp)
                    )

                    DropdownMenu(
                        expanded = isDropDownExpanded.value,
                        onDismissRequest = {
                            isDropDownExpanded.value = false
                        },
                        modifier = Modifier.background(Color.White)) {
                        year.forEachIndexed { index, year ->
                            DropdownMenuItem(text = {
                                Text(text = year)
                            },
                                onClick = {
                                    isDropDownExpanded.value = false
                                    chosenItemPosition.intValue = index
                                })
                        }
                    }
                }
            }

            Box(modifier = Modifier.padding(top = 16.dp)) {
                BarChart(barChartInputs)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            ) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Canvas(
                    modifier = Modifier
                        .size(18.dp)
                        .weight(1f)
                ) {
                    drawCircle(
                        color = Color(0xFFEEB93A),
                        radius = 6.dp.toPx(),
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
                Text(text = "Highest Income", fontSize = 12.sp)
                Spacer(
                    modifier = Modifier
                        .width(12.dp)
                        .weight(1f)
                )
                Canvas(
                    modifier = Modifier
                        .size(18.dp)
                        .weight(1f)
                ) {
                    drawCircle(
                        color = Color(0xFF4173E0),
                        radius = 6.dp.toPx(),
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
                Text(text = "Lowest Income", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun BarChart(data: Map<String, Int>) {
    val maxValue = 2800f
    val stepSize = 400
    val barWidth = 28f
    val xAxisHeight = 50f
    val padding = 24f
    val xOffset = 100f

    val maxEntry = data.maxByOrNull { it.value }
    val minEntry = data.minByOrNull { it.value }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
            .height(434.dp)
    ) {
        val chartHeight = size.height - xAxisHeight - padding
        val barSpacing = (chartHeight - padding) / data.size
        val cornerRadius = 10f

        for (i in 0..7) {
            val xPosition = xOffset + ((size.width - xOffset) / 7) * i
            drawContext.canvas.nativeCanvas.drawText(
                (i * stepSize).toString(),
                xPosition,
                30f,
                Paint().asFrameworkPaint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 10.sp.toPx()
                    textAlign = android.graphics.Paint.Align.CENTER
                    color = 0xFF616161.toInt()
                }
            )
        }

        data.entries.forEachIndexed { index, entry ->
            val barHeight = (entry.value / maxValue) * (size.width - padding)
            val yPosition = xAxisHeight + barSpacing * index + padding

            val backgroundColor = when (entry) {
                maxEntry -> Color(0xFFEEB93A)
                minEntry -> Color(0xFF4173E0)
                else -> Color.Transparent
            }

            if (backgroundColor != Color.Transparent) {
                drawRoundRect(
                    color = backgroundColor,
                    topLeft = Offset(-5f, yPosition - 10f),
                    size = Size(xOffset - 25f, barWidth + 20f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                        cornerRadius,
                        cornerRadius
                    )
                )
            }

            val textColor = if (backgroundColor != Color.Transparent) {
                android.graphics.Color.WHITE
            } else {
                0xFF616161.toInt()
            }

            val textYPosition = yPosition + barWidth / 2 + 10f

            drawContext.canvas.nativeCanvas.drawText(
                entry.key,
                0f,
                textYPosition,
                Paint().asFrameworkPaint().apply {
                    color = textColor
                    textSize = 12.sp.toPx()
                    textAlign = android.graphics.Paint.Align.LEFT
                }
            )

            val barColor = when (entry) {
                maxEntry -> Color(0xFFEEB93A)
                minEntry -> Color(0xFF4173E0)
                else -> Color(0xFF6D7890)
            }

            drawRoundRect(
                color = barColor,
                topLeft = Offset(xOffset, yPosition),
                size = Size(barHeight, barWidth),
                cornerRadius =
                androidx.compose.ui.geometry.CornerRadius(
                    4.dp.toPx(), 4.dp.toPx()
                )
            )
        }
    }
}

@Composable
fun MyCard() {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
//            .height(125.dp)
            .background(Color(0xFFFBFCFF), shape = RoundedCornerShape(11.dp)),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(modifier = Modifier.padding(top = 12.dp)) {
            Image(
                painter = painterResource(id = R.drawable.image_29),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column(
                modifier = Modifier.align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Vintage car for\nCollection",
                    fontFamily = robotoFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.size(12.dp))
                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.alarm),
                        contentDescription = null
                    )
                    Text(
                        text = " 5D:12H :2M",
                        fontFamily = robotoFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF373737)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 12.dp)
        ) {
            Text(
                modifier = Modifier
                    .width(158.dp)
                    .height(31.dp),
                text = "Lorem ipsum dolor sit amet consectetur. Czursus augummmmm",
                fontFamily = robotoFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF373737)
            )
            Spacer(Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .height(25.dp)
                    .width(55.dp)
                    .background(Color(0xFF3263AB), shape = RoundedCornerShape(4.dp))
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "See More",
                    fontSize = 10.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun LeaderBoard() {
    val selected = remember {
        mutableStateOf("sellers")
    }

    val sellersList = listOf(
        UserCardData("Dora British", R.drawable.user_pict, 1, 5.0, 200),
        UserCardData("Dora British", R.drawable.user_pict, 2, 5.0, 115),
        UserCardData("Dora British", R.drawable.user_pict, 3, 5.0, 100),
        UserCardData("Dora British", R.drawable.user_pict, 3, 5.0, 100)
    )

    val buyersList = listOf(
        UserCardData("Dora British2", R.drawable.user_pict, 1, 5.0, 200),
        UserCardData("Dora British2", R.drawable.user_pict, 2, 5.0, 115),
        UserCardData("Dora British2", R.drawable.user_pict, 3, 5.0, 100),
        UserCardData("Dora British2", R.drawable.user_pict, 3, 5.0, 100)
    )

    val userList = if (selected.value == "sellers") sellersList else buyersList

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(bottom = 24.dp),

        colors = CardDefaults.cardColors(Color(0xFFFBFCFF)),
        border = BorderStroke(1.dp, color = Color(0xFFEEEEEE))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Leader Board",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = robotoFamily
            )
            Text(
                text = "Top Sellers Of The Month",
                fontSize = 14.sp,
                color = Color(0xFF8D8D8D),
                modifier = Modifier.padding(top = 6.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .background(Color(0xFFFFFFFF)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .padding(vertical = 6.dp)
                        .padding(start = 8.dp)
                        .background(
                            if (selected.value == "sellers") Color(0xFFF7F9FD) else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            0.5.dp,
                            if (selected.value == "sellers") Color(0xFFE8EFF9) else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )
                        .clickable(
                            indication = null, // Remove ripple effect
                            interactionSource = remember { MutableInteractionSource() }
                        ) { selected.value = "sellers" },
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "Sellers",
                        modifier = Modifier.padding(vertical = 6.dp),
                        fontFamily = robotoFamily,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .padding(vertical = 6.dp)
                        .padding(end = 8.dp)
                        .background(
                            if (selected.value == "buyers") Color(0xFFF7F9FD) else Color.Transparent
                        )
                        .border(
                            0.5.dp,
                            if (selected.value == "buyers") Color(0xFFE8EFF9) else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )
                        .clickable(
                            indication = null, // Remove ripple effect
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            selected.value = "buyers"
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Buyers",
                        modifier = Modifier.padding(vertical = 6.dp),
                        fontFamily = robotoFamily,
                        textAlign = TextAlign.Center
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(userList) { user ->
                    UserCard(user)
                    Spacer(modifier = Modifier.size(4.dp))
                }
            }
        }
    }
}

data class UserCardData(
    val username: String,
    val imageId: Int,
    val place: Int,
    val stars: Double,
    val sales: Int
)

@Composable
fun UserCard(userCardData: UserCardData) {
    val username = userCardData.username
    val imageId = userCardData.imageId
    val place = userCardData.place
    val stars = userCardData.stars
    val sales = userCardData.sales

    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .background(
                Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp)
            )
            .border(0.5.dp, Color(0xFFE8EFF9), RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp)
                .padding(vertical = 9.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (place in 1..3) {
                Image(
                    painter = painterResource(
                        id = when (place) {
                            1 -> R.drawable.badge_1
                            2 -> R.drawable.badge_2
                            3 -> R.drawable.badge_3
                            else -> R.drawable.badge_1
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier

                        .size(24.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                )
            }
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = username,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 10.dp),
                fontFamily = robotoFamily
            )
        }

        Column(
            modifier = Modifier.padding(end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null,
                    tint = Color(0xFF3263AB),
                    modifier = Modifier
                        .padding(start = 6.dp, end = 2.dp)
                        .size(12.dp)
                )
                Text(
                    text = stars.toString(),
                    fontSize = 12.sp,
                    fontFamily = robotoFamily,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "$sales Sales",
                fontSize = 12.sp,
                color = Color(0xFF2D2D2D),
                fontFamily = robotoFamily,
                textAlign = TextAlign.Center
            )
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DashboardPagePreview() {
//    MyCard(text = "Active Bids", color = Color(0xFFE3F2FF), income = "5", modifier = Modifier)
    DashboardPage(role = "Buyer")
}


