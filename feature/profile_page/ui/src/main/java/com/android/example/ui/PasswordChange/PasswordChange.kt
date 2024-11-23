package com.android.example.ui.PasswordChange

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.android.example.ui.FieldsEnum
import com.android.example.ui.R
import com.android.example.ui.profilePage.ProfilePageViewModel
import com.android.example.ui.theme.robotoFamily
import com.core.common.navigation_constants.ProfilePage
import kotlinx.coroutines.delay

@Composable
fun PasswordChange(
    modifier: Modifier = Modifier, viewModel: PasswordChangeViewModel = hiltViewModel(),
    profileViewModel: ProfilePageViewModel = hiltViewModel(),
    navController: NavController,
    role: String
) {

    val statusBarHeight = with(LocalDensity.current) {
        getStatusBarHeight().toDp()
    }

    val passwordChangeState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .background(Color.White)
//            .padding(top = statusBarHeight)
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.chevron_backward),
                contentDescription = "forward icon",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.navigate("${ProfilePage.settings}/$role") {
                            popUpTo(ProfilePage.passwordChange) { inclusive = true }
                        }
                    })
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Change Password",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Password(
            isFocused = passwordChangeState.isFocused1,
            passwordChangeState.password1,
            "Current Password",
            !passwordChangeState.isPasswordValid,
            passwordChangeState.passwordVisible1,
            onClick = {
                viewModel.changeBooleanField(
                    fieldsEnum = FieldsEnum.PASSWORDVISIBLE1,
                    !passwordChangeState.passwordVisible1
                )
            },
            onValueChange = { newValue: String ->
                viewModel.changeStringField(
                    fieldsEnum = FieldsEnum.PASSWORD1, newValue
                )
            },
            onFocusChanged = { focusState ->
                viewModel.changeBooleanField(
                    fieldsEnum = FieldsEnum.ISFOCUSED1,
                    focusState.isFocused
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Password(
            isFocused = passwordChangeState.isFocused2,
            passwordChangeState.password2,
            "New Password",
            !passwordChangeState.isPasswordValid,
            passwordChangeState.passwordVisible2,
            onClick = {
                viewModel.changeBooleanField(
                    fieldsEnum = FieldsEnum.PASSWORDVISIBLE2,
                    !passwordChangeState.passwordVisible2
                )
            },
            onValueChange = { newValue: String ->
                viewModel.changeStringField(
                    fieldsEnum = FieldsEnum.PASSWORD2, newValue
                )
            },
            onFocusChanged = { focusState ->
                viewModel.changeBooleanField(
                    fieldsEnum = FieldsEnum.ISFOCUSED2,
                    focusState.isFocused
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Password(
            isFocused = passwordChangeState.isFocused3,
            passwordChangeState.password3,
            "Re-Enter Password",
            !passwordChangeState.isPasswordValid,
            passwordChangeState.passwordVisible3,
            onClick = {
                viewModel.changeBooleanField(
                    fieldsEnum = FieldsEnum.PASSWORDVISIBLE3,
                    !passwordChangeState.passwordVisible3
                )
            },
            onValueChange = { newValue: String ->
                viewModel.changeStringField(
                    fieldsEnum = FieldsEnum.PASSWORD3, newValue
                )
            },
            onFocusChanged = { focusState ->
                viewModel.changeBooleanField(
                    fieldsEnum = FieldsEnum.ISFOCUSED3,
                    focusState.isFocused
                )
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.changePassword(
                    passwordChangeState.password1,
                    passwordChangeState.password2,
                    passwordChangeState.password3,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 17.dp)
                .clickable {
                    profileViewModel.logout(navController)
                },
            shape = RoundedCornerShape(8.dp),
            enabled = passwordChangeState.password1.isNotEmpty() &&
                    passwordChangeState.password2.isNotEmpty() &&
                    passwordChangeState.password3.isNotEmpty(),
            colors = ButtonColors(
                containerColor = Color(0xFF3263AB),
                contentColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFF94A9C8),
                disabledContentColor = Color(0xFFFFFFFF)
            )
        ) {
            Text(
                text = "Change Password",
                fontSize = 14.sp,
                fontFamily = robotoFamily,
            )
        }
    }
    if (passwordChangeState.showPopup) {
        PasswordChangedPopup(onDismiss = {
            viewModel.changeBooleanField(
                FieldsEnum.SHOWPOPUP,
                false
            )
        })
    }
}

@Composable
fun getStatusBarHeight(): Int {
    val context = LocalContext.current
    val rootView = (context as? Activity)?.window?.decorView ?: return 0
    val insets = ViewCompat.getRootWindowInsets(rootView)
    return insets?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: 0
}

@Composable
fun Password(
    isFocused: Boolean,
    value: String,
    text: String,
    isError: Boolean,
    visible: Boolean,
    onClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onFocusChanged: (FocusState) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .onFocusChanged { focusState ->
                onFocusChanged(focusState)
            },
        label = {
            Text(
                text = text,
                fontFamily = robotoFamily,
                color = when {
                    isError -> Color.Red
                    isFocused -> Color(0xFF3263AB)
                    else -> Color(0xFF49454F)
                }
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color(0xFFE8EFF9),
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.LightGray
        ),
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                painter = painterResource(id = if (visible) R.drawable.visibility else R.drawable.visibility_off),
                contentDescription = null,
                tint = Color(0xFF3263AB),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onClick()
                    }
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun PasswordChangedPopup(onDismiss: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1000)
        onDismiss()
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(0.5.dp, Color(0xFF34A853)),
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFFE4FFEB), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.check_circle),
                        contentDescription = null,
                        tint = Color(0xFF34A853),
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Password changed!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = robotoFamily,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You can now log in with your new credentials.",
                    fontSize = 12.sp,
                    color = Color(0xFF545454),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = robotoFamily,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


