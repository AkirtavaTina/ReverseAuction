package com.feature.auction.ui.screen.signup


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.core.common.navigation_constants.AuthFeature
import com.feature.auction.ui.screen.ui.theme.robotoFamily


@Composable
fun RolePage(modifier: Modifier = Modifier, navController: NavController) {
    val bullet = "\u2022"
    val messagesForCons = listOf(
        "Post what you need, from products to produce.",
        "Specify the price you're willing to pay.",
        "Receive offers from various sellers.",
        "Choose the best quality and price based on offers.",
        "Use reviews to make informed decisions.",
        "Rely on us to handle all communication and delivery for a seamless experience."
    )

    val messagesForMerch = listOf(
        "Browse buyer requests and offer your products.",
        "Set competitive pricing and quality.",
        "Build your reputation with reviews.",
        "Secure sales by presenting great deals.",
        "We handle all communication and delivery for a smooth transaction.",
    )

    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))

    var selectedRole by remember { mutableStateOf("") }

    Column(
        modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = modifier.weight(1F)
                .fillMaxSize()
                .padding(top = 32.dp)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Which role would you like to choose?",
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 16.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            )

            Column(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF7F9FD),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (selectedRole == "Buyer") Color(0xFF3263AB) else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { selectedRole = "Buyer" }
                    .padding(12.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Consumer",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .weight(1f),
                        fontSize = 20.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF3263AB)
                    )

                    RadioButton(
                        selected = selectedRole == "Buyer",
                        onClick = { selectedRole = "Buyer" },
                        modifier = Modifier.scale(0.6f),
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF3263AB),
                            unselectedColor = Color.Gray
                        )
                    )
                }

                Text(
                    text = "As a Consumer, you can post your needs and price, receive offers from sellers",
                    modifier = Modifier.padding(top = 4.dp),
                    fontSize = 12.sp,
                    fontFamily = robotoFamily
                )
                Text(
                    buildAnnotatedString {
                        messagesForCons.forEach {
                            withStyle(style = paragraphStyle) {
                                append(bullet)
                                append("\t\t")
                                append(it)
                            }
                        }
                    },
                    fontSize = 11.sp,
                    fontFamily = robotoFamily,
                    color = Color(0xFF7B7B7B),
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.size(16.dp))
            Column(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF7F9FD),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (selectedRole == "Seller") Color(0xFF3263AB) else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { selectedRole = "Seller" }
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Merchant",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .weight(1f),
                        fontSize = 20.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF3263AB)
                    )

                    RadioButton(
                        selected = selectedRole == "Seller",
                        onClick = { selectedRole = "Seller" },
                        modifier = Modifier.scale(0.6f),
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF3263AB),
                            unselectedColor = Color.Gray
                        )
                    )
                }

                Text(
                    text = "As a Merchant, you can view buyer requests, offer products with competitive pricing",
                    modifier = Modifier.padding(top = 4.dp),
                    fontSize = 12.sp,
                    fontFamily = robotoFamily
                )

                Text(
                    buildAnnotatedString {
                        messagesForMerch.forEach {
                            withStyle(style = paragraphStyle) {
                                append(bullet)
                                append("\t\t")
                                append(it)
                            }
                        }
                    },
                    fontSize = 11.sp,
                    fontFamily = robotoFamily,
                    color = Color(0xFF7B7B7B),
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 16.dp, bottom = 56.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Button(
                onClick = { navController.navigate(AuthFeature.onboarding) },
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .weight(1F)
                    .padding(start = 20.dp),
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF7F9FD),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Back",
                    fontSize = 14.sp,
                    fontFamily = robotoFamily,
                    color = Color(0xFF3263AB)
                )
            }

            Spacer(modifier = Modifier.size(12.dp))

            Button(
                onClick = { navController.navigate("${AuthFeature.signUp}/${selectedRole}") },
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .weight(1F)
                    .padding(end = 20.dp),
                enabled = selectedRole.isNotEmpty(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3263AB),

                    contentColor = Color(0xFFFFFFFF),
                    disabledContainerColor = Color(0xFF94A9C8),
                    disabledContentColor = Color(0xFFFFFFFF)
                )
            ) {
                Text(
                    text = "Next",
                    fontSize = 14.sp,
                    fontFamily = robotoFamily
                )
            }
        }
    }
}