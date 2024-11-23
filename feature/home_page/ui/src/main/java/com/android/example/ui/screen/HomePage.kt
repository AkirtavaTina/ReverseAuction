package com.android.example.ui.screen

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.android.example.ui.R
import com.android.example.ui.buyer.BuyerLiveAuctions
import com.android.example.ui.seller.DashboardPage
import com.android.example.ui.seller.LiveAuctionPage
import com.android.example.ui.seller.MyButton
import com.android.example.ui.theme.robotoFamily
import com.core.common.navigation_constants.ProfilePage


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    role: String
) {
    var search by remember { mutableStateOf("") }

    val statusBarHeight = with(LocalDensity.current) {
        getStatusBarHeight().toDp()
    }

    val tabItems = listOf(
        TabItem(
            title = "Live Auctions",
            icon = R.drawable.gavel,
            selectedColor = Color(0xFF3263AB),
            unselectedColor = Color.Black
        ),
        TabItem(
            title = "Dashboard",
            icon = R.drawable.view_kanban,
            selectedColor = Color(0xFF3263AB),
            unselectedColor = Color.Black
        ),
        TabItem(
            title = "Transactions",
            icon = R.drawable.receipt_long,
            selectedColor = Color(0xFF3263AB),
            unselectedColor = Color.Black
        ),
        TabItem(
            title = "Active Bids",
            icon = R.drawable.hand_gesture,
            selectedColor = Color(0xFF3263AB),
            unselectedColor = Color.Black
        )
    )

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { tabItems.size })

    LaunchedEffect(selectedTabIndex) {
        pagerState.scrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Column(
        modifier = modifier
            .background(Color.White)
//            .padding(top = statusBarHeight)
            .fillMaxSize()
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 20.dp)
        ) {
            MyButton(R.drawable.notification, {})
            Spacer(modifier = Modifier.size(10.dp))

            OutlinedTextField(
                value = search,
                onValueChange = {
                    search = it
                },
                textStyle = TextStyle(
                    color = Color(0xFF5A5A5A),
                    fontSize = 14.sp
                ),
                placeholder = {
                    Text(
                        text = "Search for anything",
                        style = TextStyle(
                            color = Color(0xFF5A5A5A),
                            fontSize = 14.sp
                        )
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFEBEBEB),
                    unfocusedBorderColor = Color(0xFFEBEBEB),
                ),
                modifier = Modifier
                    .weight(1F)
                    .height(48.dp)
                    .border(1.dp, Color(0xFFEBEBEB), RoundedCornerShape(12.dp)),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(10.dp))
            MyButton(R.drawable.favorite, {})
            Spacer(modifier = Modifier.size(10.dp))
            MyButton(R.drawable.user) { navController.navigate("${ProfilePage.profile}/${role}") }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .background(Color.White)
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
                    when (page) {
                        0 -> {
                            if (role == "Seller") {
                                LiveAuctionPage(role)
                            } else {
                                BuyerLiveAuctions(navController = navController, role = role)
                            }
                        }
                        1 -> DashboardPage(role = role)
                    }
                }

        TabRow(
            modifier = Modifier
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.White)),
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(4.dp)
                        .offset(y = (-80.1).dp)
                        .background(Color(0xFF3263AB))
                )
            }
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    modifier = Modifier.background(Color.White),
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(
                            text = item.title,
                            fontSize = 12.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Normal,
                            color = if (index == selectedTabIndex) item.selectedColor else item.unselectedColor
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.title,
                            tint = if (index == selectedTabIndex) item.selectedColor else item.unselectedColor
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun getStatusBarHeight(): Int {
    val context = LocalContext.current
    val rootView = (context as? Activity)?.window?.decorView ?: return 0
    val insets = ViewCompat.getRootWindowInsets(rootView)

    return insets?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: 0
}


data class TabItem(
    val title: String,
    val icon: Int,
    val selectedColor: Color,
    val unselectedColor: Color
)
