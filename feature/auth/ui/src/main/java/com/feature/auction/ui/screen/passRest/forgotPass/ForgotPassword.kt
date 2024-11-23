package com.feature.auction.ui.screen.passRest.forgotPass

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.feature.auction.ui.R
import com.feature.auction.ui.screen.ui.theme.AppColors
import com.feature.auction.ui.screen.ui.theme.AppStrings
import com.feature.auction.ui.screen.ui.theme.robotoFamily


@Composable
fun ForgotPasswordPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val forgotPassStates by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Forgot Password",
            modifier = Modifier.padding(top = 155.dp),
            fontSize = 18.sp,
            fontFamily = robotoFamily
        )

        Column {
            OutlinedTextField(
                value = forgotPassStates.email,
                onValueChange = {
                    viewModel.onEmailChanged(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
                label = {
                    Text(
                        text = AppStrings.EMAIL,
                        fontFamily = robotoFamily,
                        color = if (forgotPassStates.error) Color.Red else AppColors.Black
                    )
                        },
                shape = RoundedCornerShape(8.dp),
                isError = forgotPassStates.error,
                colors = OutlinedTextFieldDefaults.colors(
                    errorBorderColor = Color.Red,
                    focusedBorderColor = AppColors.Black,
                    unfocusedBorderColor = Color(0x4049454F),
                    focusedLabelColor = AppColors.Black,
                )
            )
            if (forgotPassStates.error) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.error),
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))

                    Text(
                        text = forgotPassStates.errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp
                    )

                }
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Code will be sent to your email address",
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                color = Color(0xFF49454F)
            )

            Button(
                onClick = {
                    viewModel.sendResetCode(navController)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = forgotPassStates.isEmailValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3263AB),
                    contentColor = Color(0xFFFFFFFF),
                    disabledContainerColor = Color(0xFFEAEAEA),
                    disabledContentColor = Color(0xFF7C7C7C)
                )
            ) {
                Text(
                    text = "Send Code",
                    fontSize = 14.sp,
                    fontFamily = robotoFamily,
                    color = Color.White
                )
            }
        }
    }
}
