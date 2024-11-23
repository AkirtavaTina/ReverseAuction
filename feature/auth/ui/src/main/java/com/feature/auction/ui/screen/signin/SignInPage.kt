package com.feature.auction.ui.screen.signin


import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.core.common.navigation_constants.AuthFeature
import com.feature.auction.ui.R
import com.feature.auction.ui.screen.facebook.FacebookSignInButton
import com.feature.auction.ui.screen.ui.theme.AppColors
import com.feature.auction.ui.screen.ui.theme.AppStrings
import com.feature.auction.ui.screen.ui.theme.robotoFamily
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SignInPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {

    val signInState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = AppStrings.SIGN_IN,
            modifier = Modifier.padding(top = 146.dp, bottom = 34.dp),
            fontSize = 20.sp,
            fontFamily = robotoFamily
        )

        Column {
            OutlinedTextField(
                value = signInState.email,
                onValueChange = { newValue ->
                    newValue.filter { it != ' ' && it != '\n' }
                    viewModel.changeStringField(
                        fieldsEnum = FieldsEnum.EMAIL,
                        newValue
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                isError = !signInState.isEmailValid,
                label = {
                    Text(
                        text = AppStrings.EMAIL,
                        fontFamily = robotoFamily,
                        color = if (!signInState.isEmailValid) AppColors.RedError else AppColors.Black
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    errorBorderColor = AppColors.RedError,
                    focusedBorderColor = AppColors.Black,
                    unfocusedBorderColor = Color(0x4049454F),
                    focusedLabelColor = AppColors.Black,
                )
            )

            OutlinedTextField(
                value = signInState.password,
                onValueChange = { newValue ->
                    newValue.filter { it != ' ' && it != '\n' }
                    viewModel.changeStringField(
                        fieldsEnum = FieldsEnum.PASSWORD,
                        newValue
                    )
                },
                isError = !signInState.isPasswordValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                label = {
                    Text(
                        text = AppStrings.PASSWORD, fontFamily = robotoFamily,
                        color = if (!signInState.isPasswordValid) AppColors.RedError else AppColors.Black
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    errorBorderColor = AppColors.RedError,
                    focusedBorderColor = AppColors.Black,
                    unfocusedBorderColor = Color(0x4049454F),
                    focusedLabelColor = AppColors.Black,
                ),
                visualTransformation = if (signInState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = if (signInState.passwordVisible) R.drawable.visibility else R.drawable.visibility_off),
                        null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                viewModel.changeBooleanField(
                                    fieldsEnum = FieldsEnum.PASSWORDVISIBLE,
                                    !signInState.passwordVisible
                                )
                            }
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Text(
                text = AppStrings.FORGOT_PASSWORD,
                fontSize = 12.sp,
                fontFamily = robotoFamily,
                modifier = Modifier.clickable { navController.navigate(AuthFeature.forgotPass) }
            )
            if (!signInState.isEmailValid || !signInState.isPasswordValid) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.error),
                        contentDescription = null,
                        tint = AppColors.RedError,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    val emailError = viewModel.isEmailValid(signInState.email)
                    val passwordError =
                        if (signInState.password.isEmpty()) "Password cannot be empty." else ""

                    signInState.errorMessage = when {
                        emailError?.isNotEmpty() == true && passwordError.isNotEmpty() -> "Fields cannot be empty"
                        emailError?.isNotEmpty() == true -> emailError
                        passwordError.isNotEmpty() -> passwordError
                        else -> (if(signInState.errorMessage.isNotEmpty()) " " else "").toString()
                    }
                    if (signInState.errorMessage.isEmpty()) {
                        signInState.errorMessage = signInState.error
                    }
                    Text(
                        text = signInState.errorMessage,
                        color = AppColors.RedError,
                        fontSize = 12.sp
                    )

                    if (signInState.errorMessage.isEmpty() || signInState.errorMessage == " ") {
                        viewModel.changeBooleanField(
                            fieldsEnum = FieldsEnum.ISPASSWORDEMPTY,
                            true
                        )
                        viewModel.changeBooleanField(
                            fieldsEnum = FieldsEnum.ISEMAILEMPTY,
                            true
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                viewModel.signIn(navController)
                viewModel.changeStringField(
                    fieldsEnum = FieldsEnum.ERRORMESSAGE,
                    ""
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 36.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonColors(
                containerColor = Color(0xFF3263AB),
                contentColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFF002147),
                disabledContentColor = Color(0xFFFFFFFF)
            )
        ) {
            Text(
                text = "Sign In",
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
            FacebookSignInButton(navController = navController)
            GoogleSignInButton(viewModel, navController)
        }

        Row {
            Text(
                text = AppStrings.DONT_HAVE_ACCOUNT,
                fontSize = 12.sp,
                fontFamily = robotoFamily
            )

            Text(
                text = "Sign Up",
                fontSize = 12.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3263AB),
                modifier = Modifier.clickable {
                    navController.navigate(AuthFeature.role) {
                        popUpTo(AuthFeature.role) { inclusive = true }
                        launchSingleTop = true
                    }
                    viewModel.changeBooleanField(
                        fieldsEnum = FieldsEnum.ISEMAILEMPTY,
                        true
                    )
                    viewModel.changeBooleanField(
                        fieldsEnum = FieldsEnum.ISPASSWORDEMPTY,
                        true
                    )
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.EMAIL, "")
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.PASSWORD, "")
                })
        }
    }
}


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun GoogleSignInButton(viewModel: SignInViewModel, navController: NavController) {
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
                    viewModel.googleAuth(token, navController)
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