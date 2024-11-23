package com.feature.auction.ui.screen.signup

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.core.common.navigation_constants.AuthFeature
import com.feature.auction.domain.Country
import com.feature.auction.ui.R
import com.feature.auction.ui.screen.facebook.FacebookSignUpButton
import com.feature.auction.ui.screen.ui.theme.AppColors
import com.feature.auction.ui.screen.ui.theme.AppStrings
import com.feature.auction.ui.screen.ui.theme.robotoFamily
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(), role: String
) {
    val signUpState by viewModel.state.collectAsStateWithLifecycle()

    viewModel.changeStringField(fieldsEnum = FieldsEnum.ROLE, role)

    val tabItems = listOf(
        TabItem(AppStrings.INDIVIDUAL, AppColors.DarkBlue, AppColors.White),
        TabItem(AppStrings.LEGAL, AppColors.DarkBlue, AppColors.White)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = AppStrings.SIGN_UP,
            modifier = Modifier.padding(top = 24.dp),
            fontSize = 20.sp,
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Medium
        )

        TabRow(
            selectedTabIndex = signUpState.selectedIndex,
            modifier = Modifier
                .padding(top = 24.dp)
                .clip(RoundedCornerShape(8.dp)),
            divider = {},
            indicator = {}
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == signUpState.selectedIndex,
                    onClick = {
                        viewModel.changeIndex(fieldsEnum = FieldsEnum.SELECTEDINDEX, index)
                        viewModel.changeBooleanField(FieldsEnum.ISNAMEVALID, true)
                        viewModel.changeBooleanField(FieldsEnum.ISSURNAMEVALID, true)
                        viewModel.changeBooleanField(FieldsEnum.ISLEGALNAMEEMPTY, true)
                        viewModel.changeBooleanField(FieldsEnum.ISIDEMPTY, true)
                        viewModel.changeBooleanField(FieldsEnum.ISEMAILVALID, true)
                        viewModel.changeBooleanField(FieldsEnum.ISPASSWORDVALID, true)
                        viewModel.changeBooleanField(FieldsEnum.ISCONFIRMATIONVALID, true)
                        viewModel.changeBooleanField(FieldsEnum.ISSELECTIONEMPTY, false)
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.NAME, "")
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.SURNAME, "")
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.LEGALNAME, "")
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.IDCODE, "")
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.EMAIL, "")
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.PASSWORD, "")
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.CONFIRMPASSWORD, "")
                    },
                    modifier = Modifier
                        .height(32.dp)
                        .background(
                            color = if (index == signUpState.selectedIndex) item.selectedColor else item.unselectedColor
                        ),
                    text = {
                        Text(
                            text = item.title,
                            color = if (index == signUpState.selectedIndex) AppColors.White else Color(
                                0xFF6A7A92
                            ),
                            fontFamily = robotoFamily,
                            fontSize = 14.sp
                        )
                    },
                )
            }
        }

        Column(modifier = Modifier.padding(top = 24.dp)) {

            if (signUpState.selectedIndex == 0) {
                TextField(
                    title = signUpState.name,
                    changeValue = { newValue ->
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.NAME, newValue)
                        viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISNAMEVALID, true)
                    },
                    AppStrings.NAME_FIELD,
                    isError = !signUpState.isNameValid,
                    "Make sure it's not empty."
                )

                TextField(
                    title = signUpState.surname,
                    changeValue = { newValue ->
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.SURNAME, newValue)
                        viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISSURNAMEVALID, true)
                    },
                    AppStrings.SURNAME_FIELD,
                    isError = !signUpState.isSurnameValid,
                    "Make sure it's not empty."
                )
            } else {
                TextField(
                    title = signUpState.legalName,
                    changeValue = { newValue ->
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.LEGALNAME, newValue)
                        viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISLEGALNAMEEMPTY, true)

                    },
                    AppStrings.LEGAL_NAME_FIELD,
                    isError = !signUpState.isLegalNameValid,
                    "Make sure it's not empty.",
                )
                TextField(
                    title = signUpState.idCode,
                    changeValue = { newValue ->
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.IDCODE, newValue)
                        viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISIDEMPTY, true)
                    },
                    AppStrings.ID_CODE,
                    isError = !signUpState.isIdValid,
                    "Make sure it's not empty.",
                )
            }

            TextField(
                title = signUpState.email, changeValue = { newValue ->
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.EMAIL, newValue)
                    viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISEMAILVALID, true)
                },
                AppStrings.EMAIL, isError = !signUpState.isEmailValid,
                if (viewModel.isEmailValid(signUpState.email) != null) "Invalid email format." else "user with this email already exists."
            )

            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                OutlinedTextField(
                    value = signUpState.password,
                    onValueChange = { newValue ->
                        viewModel.changeStringField(fieldsEnum = FieldsEnum.PASSWORD, newValue)
                        viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISPASSWORDVALID, true)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    isError = !signUpState.isPasswordValid,
                    label = {
                        Text(
                            text = AppStrings.PASSWORD,
                            fontFamily = robotoFamily,
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        errorBorderColor = AppColors.RedError,
                        focusedBorderColor = AppColors.Black,
                        unfocusedBorderColor = Color(0x4049454F),
                        focusedLabelColor = AppColors.Black,
                    ),
                    visualTransformation = if (signUpState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = if (signUpState.passwordVisible) R.drawable.visibility else R.drawable.visibility_off),
                            null,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    viewModel.changeBooleanField(
                                        FieldsEnum.PASSWORDVISIBLE,
                                        !signUpState.passwordVisible
                                    )
                                }
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Default
                    ),
                    keyboardActions = KeyboardActions.Default
                )
                if (!signUpState.isPasswordValid) {
                    val passValid = viewModel.validatePassword(signUpState.password)?.let {
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
                    viewModel.changeBooleanField(FieldsEnum.ISPASSWORDVALID, passValid == null)
                }
            }
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                OutlinedTextField(
                    value = signUpState.confirmPassword,
                    onValueChange = { newValue ->
                        viewModel.changeStringField(FieldsEnum.CONFIRMPASSWORD, newValue)
                        viewModel.changeBooleanField(FieldsEnum.ISCONFIRMATIONVALID, true)

                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = AppStrings.CONFIRM_PASSWORD,
                            fontFamily = robotoFamily,
                        )
                    },
                    isError = !signUpState.isConfirmationValid,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        errorBorderColor = AppColors.RedError,
                        focusedBorderColor = AppColors.Black,
                        unfocusedBorderColor = Color(0x4049454F),
                        focusedLabelColor = AppColors.Black,
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(
                                id = if (signUpState.confirmedPasswordVisible) {
                                    R.drawable.visibility
                                } else R.drawable.visibility_off
                            ),
                            null,
                            tint = if (!signUpState.isConfirmationValid) AppColors.RedError else Color.Unspecified,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    viewModel.changeBooleanField(
                                        fieldsEnum = FieldsEnum.CONFIRMEDPASSWORDVISIBLE,
                                        !signUpState.confirmedPasswordVisible
                                    )
                                }
                        )
                    },
                    visualTransformation = if (signUpState.confirmedPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Default
                    ),
                    keyboardActions = KeyboardActions.Default
                )
                if (!signUpState.isConfirmationValid) {
                    val passValid = viewModel.validateConfirmPassword(
                        signUpState.confirmPassword,
                        signUpState.password
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
                    viewModel.changeBooleanField(FieldsEnum.ISCONFIRMATIONVALID, passValid == null)
                }
            }

            val countries = Country.entries

            ExposedDropdownMenuBox(
                expanded = signUpState.expanded,
                onExpandedChange = {
                    viewModel.changeBooleanField(FieldsEnum.EXPANDED, !signUpState.expanded)
                }
            ) {
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    OutlinedTextField(
                        value = countries.firstOrNull { it.code == signUpState.selectedItem }?.fullName
                            ?: "",
                        onValueChange = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        label = { Text(text = AppStrings.COUNTRY, fontFamily = robotoFamily) },
                        shape = RoundedCornerShape(8.dp),
                        isError = signUpState.isSelectionEmpty,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = AppColors.Transparent,
                            unfocusedContainerColor = AppColors.Transparent,
                            disabledContainerColor = AppColors.Transparent,
                            errorBorderColor = AppColors.RedError,
                            focusedBorderColor = AppColors.Black,
                            unfocusedBorderColor = Color(0x4049454F)
                        ),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_down),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                    if (signUpState.isSelectionEmpty) {
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
                                text = "Country selection is required.",
                                color = AppColors.RedError,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                ExposedDropdownMenu(
                    expanded = signUpState.expanded,
                    onDismissRequest = {
                        viewModel.changeBooleanField(
                            fieldsEnum = FieldsEnum.EXPANDED,
                            false
                        )
                    },
                    modifier = Modifier.background(Color.White)
                ) {
                    countries.forEach { country ->
                        DropdownMenuItem(
                            text = { Text(country.fullName) },
                            onClick = {
                                viewModel.changeStringField(
                                    fieldsEnum = FieldsEnum.SELECTEDITEM,
                                    value = country.code
                                )
                                viewModel.changeBooleanField(
                                    fieldsEnum = FieldsEnum.ISSELECTIONEMPTY,
                                    value = false
                                )
                                viewModel.changeBooleanField(
                                    fieldsEnum = FieldsEnum.EXPANDED,
                                    false
                                )
                            }
                        )
                    }
                }
            }
            Row {
                Checkbox(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(16.dp),
                    checked = signUpState.isChecked,
                    onCheckedChange = { newValue ->
                        viewModel.changeBooleanField(
                            fieldsEnum = FieldsEnum.ISCHECKED,
                            value = newValue
                        )
                    }
                )
                Text(
                    buildAnnotatedString {
                        append("By creating an account, you agree to our ")
                        withStyle(style = SpanStyle(color = Color(0xFF3263AB))) {
                            append(AppStrings.TERMS)
                        }
                        append(" and ")
                        withStyle(style = SpanStyle(color = Color(0xFF3263AB))) {
                            append(AppStrings.PRIVACY)
                        }
                        append(".")
                    },
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 12.sp,
                    fontFamily = robotoFamily,
                    lineHeight = 18.sp
                )
            }
        }

        Button(
            onClick = {
                viewModel.signup(navController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = signUpState.isChecked,
            colors = ButtonColors(
                containerColor = AppColors.ButtonColor,
                contentColor = AppColors.ButtonContentColor,
                disabledContainerColor = AppColors.DisabledButtonColor,
                disabledContentColor = AppColors.ButtonContentColor
            )
        ) {
            Text(
                text = "Sign Up",
                color = AppColors.ButtonContentColor,
                fontSize = 14.sp,
                fontFamily = robotoFamily
            )
        }
        if (signUpState.selectedIndex == 0) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(2F),
                    thickness = 1.dp,
                    color = AppColors.DividerColor
                )
                Text(
                    AppStrings.OR,
                    modifier = Modifier.weight(1F),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = robotoFamily,
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    modifier = Modifier.weight(2F),
                    thickness = 1.dp,
                    color = AppColors.DividerColor
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                FacebookSignUpButton(navController = navController, role = role)
                GoogleSignUpButton(viewModel, role, navController)
            }
        }

        Row(modifier = Modifier.padding(bottom = 21.dp)) {
            Text(
                text = AppStrings.ALREADY_HAVE_ACCOUNT,
                fontSize = 12.sp,
                fontFamily = robotoFamily
            )

            Text(
                text = AppStrings.SIGN_IN,
                fontSize = 12.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold,
                color = AppColors.SignInColor,
                modifier = Modifier.clickable {
                    viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISNAMEVALID, true)
                    viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISSURNAMEVALID, true)
                    viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISEMAILVALID, true)
                    viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISPASSWORDVALID, true)
                    viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISCONFIRMATIONVALID, true)
                    viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISSELECTIONEMPTY, false)
                    viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISLEGALNAMEEMPTY, true)
                    viewModel.changeBooleanField(fieldsEnum = FieldsEnum.ISIDEMPTY, true)
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.NAME, "")
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.SURNAME, "")
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.LEGALNAME, "")
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.IDCODE, "")
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.EMAIL, "")
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.PASSWORD, "")
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.CONFIRMPASSWORD, "")

                    navController.navigate(AuthFeature.signIn) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun TextField(
    title: String,
    changeValue: (String) -> Unit,
    holderText: String,
    isError: Boolean,
    errorMessage: String,
) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { newValue ->

                val filteredValue = if (holderText == "Email Address") {
                    newValue.filter { it != ' ' && it != '\n' }
                } else if (holderText == "Identification Code") {
                    Log.d("holderText", holderText)
                    newValue.filter { it.isDigit() && it.isLetter() }
                } else {
                    newValue.filter { it.isLetter() }
                }
                changeValue(filteredValue)
            },
            modifier = Modifier
                .fillMaxWidth(),
            isError = isError,
            label = {
                Text(
                    text = holderText,
                    fontFamily = robotoFamily,
                    color = if (isError) AppColors.RedError else AppColors.Black
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = AppColors.RedError,
                focusedBorderColor = AppColors.Black,
                unfocusedBorderColor = Color(0x4049454F),
                focusedLabelColor = AppColors.Black,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
            keyboardActions = KeyboardActions.Default
        )
        if (isError) {
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
                    text = errorMessage,
                    color = AppColors.RedError,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

data class TabItem(
    val title: String,
    val selectedColor: Color,
    val unselectedColor: Color
)


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun GoogleSignUpButton(viewModel: SignUpViewModel, role: String, navController: NavController) {
    val context = LocalContext.current

    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("860913812719-mttu1hp83bm2li3tjqfc2sdjk3hetdvp.apps.googleusercontent.com")
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }


    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val token = account?.idToken
                if (token != null) {
                    viewModel.googleAuth(token, role, navController)
                }
            } catch (e: ApiException) {
                e.printStackTrace()
                Log.d("GoogleSignInButton", "GoogleSignInButton: $e")
            }
        }

    Box(
        modifier = Modifier
            .size(32.dp)
            .background(Color(0xFFF7F9FD), shape = RoundedCornerShape(16.dp))
            .clickable {
                googleSignInClient
                    .revokeAccess()
                    .addOnCompleteListener {
                        val signInIntent = googleSignInClient.signInIntent
                        launcher.launch(signInIntent)
                    }
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = "google",
            modifier = Modifier.size(16.dp)
        )
    }
}


