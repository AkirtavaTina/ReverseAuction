package com.feature.auction.ui.screen.signin


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.core.common.navigation_constants.AuthFeature
import com.feature.auction.ui.R
import com.feature.auction.ui.screen.ui.theme.AppStrings
import com.feature.auction.ui.screen.ui.theme.ReverseAuctionTheme
import com.feature.auction.ui.screen.ui.theme.robotoFamily


@Composable
fun SignInPage(modifier: Modifier = Modifier, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        Text(
            text = AppStrings.SIGN_IN,
            modifier = Modifier.padding(top = 148.dp, bottom = 24.dp),
            fontSize = 20.sp,
            fontFamily = robotoFamily
        )

        Column {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = {
                    Text(text = AppStrings.EMAIL, fontFamily = robotoFamily)
                },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF000000),
                    unfocusedBorderColor = Color(0xFFADADAD)
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                label = {
                    Text(text = AppStrings.PASSWORD, fontFamily = robotoFamily)
                },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF000000),
                    unfocusedBorderColor = Color(0xFFADADAD)
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = if (passwordVisible) R.drawable.visibility else R.drawable.visibility_off),
                        null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Text(
                text = AppStrings.FORGOT_PASSWORD,
                fontSize = 12.sp,
                fontFamily = robotoFamily,
                modifier = Modifier.clickable { navController.navigate(AuthFeature.forgotPass)}
            )
        }

        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 36.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonColors(
                containerColor = Color(0xFF17213D),
                contentColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFF002147),
                disabledContentColor = Color(0xFFFFFFFF)
            )
        ) {
            Text(
                text = AppStrings.SIGN_IN,
                color = Color(0xFFFFFFFF),
                fontSize = 14.sp,
                fontFamily = robotoFamily
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(2F),
                thickness = 1.dp,
                color = Color(0x9918345E)
            )
            Text(
                AppStrings.OR,
                modifier = Modifier.weight(1F),
                fontSize = 16.sp,
                fontFamily = robotoFamily,
                textAlign = TextAlign.Center
            )
            HorizontalDivider(
                modifier = Modifier.weight(2F),
                thickness = 1.dp,
                color = Color(0x9918345E)
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(0.75.dp, color = Color.Black, shape = RoundedCornerShape(100))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "facebook",
                    modifier = Modifier
                        .height(18.dp)
                        .width(12.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(0.75.dp, color = Color.Black, shape = RoundedCornerShape(100))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "google",
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Row {
            Text(
                text = AppStrings.DONT_HAVE_ACCOUNT,
                fontSize = 12.sp,
                fontFamily = robotoFamily
            )

            Text(
                text = AppStrings.SIGN_UP,
                fontSize = 12.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF9B17B),
                modifier = Modifier.clickable { navController.navigate(AuthFeature.signUp) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    ReverseAuctionTheme {
//        SignInPage(Modifier)
    }
}