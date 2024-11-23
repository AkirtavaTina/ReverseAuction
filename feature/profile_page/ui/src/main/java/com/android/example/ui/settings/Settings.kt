package com.android.example.ui.settings

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.android.example.ui.FieldsEnum
import com.android.example.ui.R
import com.android.example.ui.profileInfo.getStatusBarHeight
import com.android.example.ui.theme.Colors
import com.android.example.ui.theme.robotoFamily
import com.core.common.navigation_constants.ProfilePage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    modifier: Modifier = Modifier, navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel(),
    role: String
) {
    val settingsStates by viewModel.state.collectAsStateWithLifecycle()

    val statusBarHeight = with(LocalDensity.current) {
        getStatusBarHeight().toDp()
    }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(Color.White)
//            .padding(top = statusBarHeight)
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(top = 8.dp)
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(painter = painterResource(id = R.drawable.chevron_backward),
                contentDescription = "forward icon",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.navigate("${ProfilePage.profile}/${role}") {
                            popUpTo(ProfilePage.settings) { inclusive = true }
                        }
                    })
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Settings", fontFamily = robotoFamily, fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
        }

        Column(
            modifier = Modifier
                .weight(1F)
                .padding(top = 24.dp)
                .border(1.dp, Color(0xFFF7F9FD), RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp))
                .fillMaxHeight()
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Fields("Change Password", R.drawable.encrypted, 0xFF000000, 0xFF3263AB) {
                    navController.navigate("${ ProfilePage.passwordChange }/$role")
                }
                Spacer(modifier = Modifier.size(10.dp))
                Fields("Notifications", R.drawable.notifications_active, 0xFF000000, 0xFF3263AB) {
                    navController.navigate("${ProfilePage.notifications}/$role")
                }
                Spacer(modifier = Modifier.size(10.dp))
                Fields("Delete Account", R.drawable.delete_forever, 0xFFF24646, 0xFFF24646) {
                    navController.navigate("${ProfilePage.deleteAccount}/$role")
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .background(Color(0xFFF7F9FD), shape = RoundedCornerShape(16.dp)),
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp),
                    text = "Multi Factor Authorization",
                    fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp
                )
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.width(225.dp),
                        text = "MFA is now active, adding an extra layer of " +
                                "security to your account. You’ll need to verify " +
                                "your identity during each login.",
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        letterSpacing = 0.4.sp
                    )
                    Spacer(Modifier.size(26.dp))
                    CustomSwitch(
                        modifier = Modifier.padding(end = 10.dp),
                        checked = settingsStates.mfaActive,
                        onCheckedChange = {
                            viewModel.changeBooleanField(
                                FieldsEnum.MFAACTIVE,
                                !settingsStates.mfaActive
                            )
                        },
                    )

                }
            }
            Spacer(modifier = Modifier.size(24.dp))
            val methods = listOf(
                "Email",
                "Phone"
            )
            if (settingsStates.mfaActive) {
                ExposedDropdownMenuBox(
                    expanded = settingsStates.expanded3,
                    onExpandedChange = {
                        viewModel.changeBooleanField(
                            fieldsEnum = FieldsEnum.EXPANDED3,
                            !settingsStates.expanded3
                        )
                    }
                ) {
                    Column {
                        OutlinedTextField(
                            value = settingsStates.selectedItem3,
                            onValueChange = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            readOnly = true,
                            shape = RoundedCornerShape(12.dp),
                            label = {
                                Text(
                                    text = "Select Method",
                                    fontFamily = robotoFamily,
                                    color = Color(0xFF49454F)
                                )
                            },
                            isError = settingsStates.isSelectionEmpty3,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Colors.Black,
                                unfocusedBorderColor = Color(0xFFE8EFF9),
                                focusedLabelColor = Colors.Black,
                                unfocusedLabelColor = Colors.LightGray
                            ),

                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_down),
                                    null,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                        )
                        if (settingsStates.isSelectionEmpty3) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(start = 6.dp)
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
                                    text = "Method selection is required.",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 2.dp),
                                    fontFamily = robotoFamily
                                )
                            }
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = settingsStates.expanded3,
                        onDismissRequest = {
                            viewModel.changeBooleanField(fieldsEnum = FieldsEnum.EXPANDED3, false)
                        },
                        modifier = Modifier.background(Color.White)
                    ) {
                        methods.forEach { method ->
                            DropdownMenuItem(
                                text = { Text(method) },
                                onClick = {
                                    viewModel.changeStringField(
                                        fieldsEnum = FieldsEnum.SELECTEDITEM3,
                                        value = method
                                    )
                                    viewModel.changeBooleanField(
                                        fieldsEnum = FieldsEnum.ISSELECTIONEMPTY3,
                                        value = false
                                    )
                                    viewModel.changeBooleanField(
                                        fieldsEnum = FieldsEnum.EXPANDED3,
                                        false
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val thumbSize = 24.dp
    val trackHeight = 32.dp
    val trackWidth = 52.dp
    val padding = 4.dp

    Box(
        modifier = modifier
            .size(trackWidth, trackHeight)
            .clip(RoundedCornerShape(50))
            .background(if (checked) Color(0xFF3263AB) else Color(0xFF888888))
            .clickable { onCheckedChange(!checked) }
            .padding(padding)
    ) {
        Box(
            modifier = Modifier
                .size(thumbSize)
                .clip(CircleShape)
                .background(Color.White)
                .align(if (checked) Alignment.CenterEnd else Alignment.CenterStart)
        )
    }
}


@Composable
fun Fields(title: String, id: Int, textColor: Long, color: Long, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable {
        onClick()
    }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = id),
                        contentDescription = null,
                        tint = Color(color)
                    )
                }
                Text(
                    text = title,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 18.dp),
                    color = Color(textColor)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.chevron_forward),
                contentDescription = "forward icon",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color(color))
            )
        }
    }
    HorizontalDivider(
        color = Color(0xFFF3F3F3), modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
    )
}

//@Preview(showBackground = true)
//@Composable
//fun SettingsPreview() {
////    Fields("Change Password", R.drawable.encrypted)
//    Settings()
//}