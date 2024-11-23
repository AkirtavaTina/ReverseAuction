package com.feature.auction.ui.screen.passRest.resetPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.feature.auction.ui.R
import com.feature.auction.ui.screen.ui.theme.AppColors
import com.feature.auction.ui.screen.ui.theme.robotoFamily


@Composable
fun ResetPasswordPage(
    modifier: Modifier = Modifier,
    token: String?,
    uidb: String?,
    viewModel: ResetPassViewModel = hiltViewModel(),
    navController: NavController
) {
    val resetPassStates by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Reset Your Password",
            modifier = Modifier.padding(top = 155.dp),
            fontSize = 18.sp,
            fontFamily = robotoFamily
        )


        Column {
            OutlinedTextField(
                value = resetPassStates.newPass,
                onValueChange = { viewModel.onPasswordChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
                label = { Text(text = "New Password", fontFamily = robotoFamily) },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF000000),
                    unfocusedBorderColor = Color(0xFFADADAD)
                ),
                isError = !resetPassStates.isPasswordValid,
                visualTransformation = if (resetPassStates.newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = if (resetPassStates.newPasswordVisible) R.drawable.visibility else R.drawable.visibility_off),
                        null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                viewModel.passVisible(!resetPassStates.newPasswordVisible)
                            }
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            if (!resetPassStates.isPasswordValid) {
                val passValid = viewModel.validatePassword(resetPassStates.newPass)?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .padding(top = 2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.error),
                            null,
                            tint = AppColors.RedError,
                            modifier = Modifier
                                .size(12.dp)
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            text = it,
                            color = AppColors.RedError,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
                resetPassStates.isPasswordValid = (passValid == null)
            }

            OutlinedTextField(
                value = resetPassStates.confirmedPass,
                onValueChange = { viewModel.onConfirmChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                placeholder = { Text(text = "Confirm Password", fontFamily = robotoFamily) },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF000000),
                    unfocusedBorderColor = Color(0xFFADADAD)
                ),
                isError = !resetPassStates.isConfirmationValid,
                visualTransformation = if (resetPassStates.confirmedPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = if (resetPassStates.confirmedPasswordVisible) R.drawable.visibility else R.drawable.visibility_off),
                        null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                viewModel.confirmedVisible(!resetPassStates.confirmedPasswordVisible)
                            }
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            if (!resetPassStates.isConfirmationValid) {
                val passValid = viewModel.validateConfirmPassword(
                    resetPassStates.confirmedPass,
                    resetPassStates.newPass
                )
                    ?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .padding(top = 2.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.error),
                                null,
                                tint = AppColors.RedError,
                                modifier = Modifier
                                    .size(12.dp)
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                text = it,
                                color = AppColors.RedError,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                resetPassStates.isConfirmationValid = (passValid == null)
            }
            if (resetPassStates.errorMessage.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .padding(top = 2.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.error),
                        null,
                        tint = AppColors.RedError,
                        modifier = Modifier
                            .size(12.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = resetPassStates.errorMessage,
                        color = AppColors.RedError,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(36.dp))
        Button(
            onClick = {
                viewModel.resetPassword(
                    resetPassStates.newPass,
                    resetPassStates.confirmedPass,
                    navController = navController,
                    token = token,
                    uidb = uidb
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 378.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonColors(
                containerColor = Color(0xFF3263AB),
                contentColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFFDCDCDC),
                disabledContentColor = Color(0xFFFFFFFF)
            )
        ) {
            Text(
                text = "Reset Password",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight(500)
            )
        }
    }
}

