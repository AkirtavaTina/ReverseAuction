package com.feature.auction.ui.screen.passRest.verification

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.feature.auction.ui.R
import com.feature.auction.ui.screen.ui.theme.robotoFamily


@Composable
fun VerificationPage(
    modifier: Modifier = Modifier,
    viewModel: VerificationViewModel = hiltViewModel(),
    userId: String?,
    email: String?,
    type: String?,

    navController: NavController
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
            .background(Color.White)
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

        val focusManager = LocalFocusManager.current

        Row(modifier = Modifier.padding(top = 16.dp)) {
            verificationState.error = verificationState.error && allFieldsFilled
            SingleDigitField(
                value = verificationState.number1,
                onValueChange = { newValue ->
                    viewModel.changeNumberField(
                        fieldsEnum = FieldsEnum.NUMBER1,
                        newValue
                    )
                },
                focusManager = focusManager,
                isError = verificationState.error
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
                focusManager = focusManager,
                isError = verificationState.error
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
                focusManager = focusManager,
                isError = verificationState.error
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
                focusManager = focusManager,
                isError = verificationState.error
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
                focusManager = focusManager,
                isError = verificationState.error
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
                focusManager = focusManager,
                isError = verificationState.error
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        if (verificationState.error) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.error),
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = verificationState.errorMessage,
                    color = Color.Red,
                    fontSize = 11.sp
                )
            }
        }


        Text(
            modifier = Modifier
                .padding(top = 12.dp)
                .clickable {
                    viewModel.resend(userId)
                    viewModel.changeNumberField(fieldsEnum = FieldsEnum.NUMBER1, "")
                    viewModel.changeNumberField(fieldsEnum = FieldsEnum.NUMBER2, "")
                    viewModel.changeNumberField(fieldsEnum = FieldsEnum.NUMBER3, "")
                    viewModel.changeNumberField(fieldsEnum = FieldsEnum.NUMBER4, "")
                    viewModel.changeNumberField(fieldsEnum = FieldsEnum.NUMBER5, "")
                    viewModel.changeNumberField(fieldsEnum = FieldsEnum.NUMBER6, "")
                },
            text = "Resend code",
            fontSize = 12.sp,
            fontFamily = robotoFamily,
            color = Color(0xFFFFA26D),
            textDecoration = TextDecoration.Underline
        )

        Button(
            onClick = {
                viewModel.verify(userId, type, navController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = allFieldsFilled,
            colors = ButtonColors(
                containerColor = Color(0xFF3263AB),
                contentColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFF94A9C8),
                disabledContentColor = Color(0xFFFFFFFF)
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
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
    isError: Boolean
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .border(
                0.5.dp,
                color = if (isError) Color.Red else Color(0xCC000000),
                shape = RoundedCornerShape(10.dp)
            ),
    ) {

        BasicTextField(
            modifier = modifier
                .align(Alignment.Center),

            singleLine = true,
            value = value,
            onValueChange = {
                if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                    onValueChange(it)
                    if (it.isNotEmpty()) {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                }
            },
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Light
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),

            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )

    }
}

