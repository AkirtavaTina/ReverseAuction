package com.android.example.ui.deleteAccount

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.android.example.ui.FieldsEnum
import com.android.example.ui.R
import com.android.example.ui.profileInfo.getStatusBarHeight
import com.android.example.ui.theme.robotoFamily
import com.core.common.navigation_constants.ProfilePage

@Composable
fun DeleteAccount(
    modifier: Modifier = Modifier, viewModel: DeleteAccountViewModel = hiltViewModel(),
    navController: NavController,
    role: String
) {

    val deleteAccountState by viewModel.state.collectAsStateWithLifecycle()

    val statusBarHeight = with(LocalDensity.current) {
        getStatusBarHeight().toDp()
    }

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
                            popUpTo(ProfilePage.deleteAccount) { inclusive = true }
                        }
                    })
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Delete Account",
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
        }


        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Enter the current password to delete the account",
            modifier = Modifier.padding(horizontal = 20.dp),
            fontFamily = robotoFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
        Password(
            isFocused = deleteAccountState.isFocused,
            value = deleteAccountState.password,
            text ="Current Password",
            isError = !deleteAccountState.isPasswordValid,
            errorMessage = deleteAccountState.error,
            deleteAccountState.passwordVisible,
            onClick = {
                viewModel.changeBooleanField(
                    fieldsEnum = FieldsEnum.PASSWORDVISIBLE,
                    !deleteAccountState.passwordVisible
                )
            },
            onValueChange = { newValue: String ->
                viewModel.changeStringField(
                    fieldsEnum = FieldsEnum.PASSWORD, newValue
                )
                viewModel.changeBooleanField(
                    fieldsEnum = FieldsEnum.ISPASSWORDVALID, true
                )
                Log.d("isError", deleteAccountState.isPasswordValid.toString())
            },
            onFocusChanged = { focusState ->
                viewModel.changeBooleanField(
                    fieldsEnum = FieldsEnum.ISFOCUSED,
                    focusState.isFocused
                )
            }
        )


        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.changeBooleanField(FieldsEnum.SHOWDIALOG, true)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 17.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = deleteAccountState.password.isNotEmpty(),
            colors = ButtonColors(
                containerColor = Color(0xFFF24646),
                contentColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFFF5B5C1),
                disabledContentColor = Color(0xFFFFFFFF)
            )
        ) {
            Text(
                text = "Delete Account",
                fontSize = 14.sp,
                fontFamily = robotoFamily,
            )
        }
        if (deleteAccountState.showDialog) {
            DeleteAccountDialog(
                onDismiss = {
                    viewModel.changeBooleanField(FieldsEnum.SHOWDIALOG, false)
                },
                onDelete = {
                    viewModel.changeBooleanField(FieldsEnum.SHOWDIALOG, false)
                    viewModel.delete(deleteAccountState.password, navController)
                }
            )
        }
    }
}


@Composable
fun Password(
    isFocused: Boolean,
    value: String,
    text: String,
    isError: Boolean,
    errorMessage: String,
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
    if (isError) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 2.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.error),
                null,
                tint = Color.Red,
                modifier = Modifier
                    .size(12.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = errorMessage,
                fontFamily = robotoFamily,
                color = Color.Red,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
fun DeleteAccountDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier
                        .size(40.dp)
                        .background(Color(0xFFF7F9FD), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_forever), // Replace with your delete icon
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.height(17.35.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Delete Account",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = robotoFamily,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Are you sure you want to delete your account? Deleting your account will remove all of your information from our database. This cannot be undone.",
                    fontSize = 16.sp,
                    color = Color(0xFF545454),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    textAlign = TextAlign.Center,
                    fontFamily = robotoFamily,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7F9FD)),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Cancel", color = Color(0xFF3263AB), fontFamily = robotoFamily, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { onDelete() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF24646)),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Delete", color = Color.White, fontFamily = robotoFamily, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
