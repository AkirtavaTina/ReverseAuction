package com.feature.auction.ui.screen.passRest.resetPassword

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.auction.ui.screen.passRest.verification.FieldsEnum
import com.feature.auction.ui.screen.passRest.verification.VerificationViewModel
import com.feature.auction.ui.screen.ui.theme.AppColors
import com.feature.auction.ui.screen.ui.theme.ReverseAuctionTheme
import com.feature.auction.ui.screen.ui.theme.robotoFamily


@Composable
fun ResetCodePage(
    modifier: Modifier = Modifier,
    viewModel: VerificationViewModel = hiltViewModel(),
//    userId: String?,
    email: String?,
//    navController: NavController
) {

    val verificationState by viewModel.state.collectAsStateWithLifecycle()

    val fullText = "Please enter the 6 digit code sent to your email address "

    val allFieldsFilled =
        listOf(
            verificationState.number1,
            verificationState.number2,
            verificationState.number3,
            verificationState.number4,
            verificationState.number5,
            verificationState.number6
        ).all { it.length == 1 }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter Code",
            modifier = Modifier.padding(top = 155.dp),
            fontSize = 18.sp,
            fontFamily = robotoFamily
        )

        Text(
            text = buildAnnotatedString {
                append(fullText)
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF18345E)
                    )
                ) {
                    append(email)
                }
            },
            fontFamily = robotoFamily,
            fontSize = 15.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .padding(top = 36.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Row(modifier = Modifier.padding(top = 16.dp)) {
            SingleDigitField(
                value = verificationState.number1,
                onValueChange = { newValue ->
                    viewModel.changeNumberField(
                        fieldsEnum = FieldsEnum.NUMBER1,
                        newValue
                    )
                },
            )
            Spacer(modifier = Modifier.size(8.dp))
            SingleDigitField(
                value = verificationState.number2,
                onValueChange = { newValue ->
                    viewModel.changeNumberField(
                        fieldsEnum = FieldsEnum.NUMBER2,
                        newValue
                    )
                },
            )
            Spacer(modifier = Modifier.size(8.dp))
            SingleDigitField(
                value = verificationState.number3,
                onValueChange = { newValue ->
                    viewModel.changeNumberField(
                        fieldsEnum = FieldsEnum.NUMBER3,
                        newValue
                    )
                },
            )
            Spacer(modifier = Modifier.size(8.dp))
            SingleDigitField(
                value = verificationState.number4,
                onValueChange = { newValue ->
                    viewModel.changeNumberField(
                        fieldsEnum = FieldsEnum.NUMBER4,
                        newValue
                    )
                },
            )
            Spacer(modifier = Modifier.size(8.dp))
            SingleDigitField(
                value = verificationState.number5,
                onValueChange = { newValue ->
                    viewModel.changeNumberField(
                        fieldsEnum = FieldsEnum.NUMBER5,
                        newValue
                    )
                },
            )
            Spacer(modifier = Modifier.size(8.dp))
            SingleDigitField(
                value = verificationState.number6,
                onValueChange = { newValue ->
                    viewModel.changeNumberField(
                        fieldsEnum = FieldsEnum.NUMBER6,
                        newValue
                    )
                },
            )
        }

        Text(
            modifier = Modifier.padding(top = 12.dp).clickable { },
            text = "Resend code",
            fontSize = 12.sp,
            fontFamily = robotoFamily,
            color = AppColors.SignInColor,
        )

        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = allFieldsFilled,
            colors = ButtonColors(
                containerColor = Color(0xFF2D3250),
                contentColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFFEAEAEA),
                disabledContentColor = Color(0xFF7C7C7C)
            )
        ) {
            Text(
                text = "Verify",
                fontSize = 14.sp,
                fontFamily = robotoFamily,
            )
        }
    }
}

@Composable
fun SingleDigitField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .border(0.5.dp, color = Color(0xCC000000), shape = RoundedCornerShape(10.dp)),
    ) {

        BasicTextField(
            modifier = modifier
                .align(Alignment.Center),

            singleLine = true,
            value = value,
            onValueChange = {
                if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                    onValueChange(it)
                }
            },

            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Light
            )
        )

    }
}


@Preview(showBackground = true)
@Composable
fun ResetPassPreview() {
        ResetCodePage(email = "Akirtava31@gmail.com")
}