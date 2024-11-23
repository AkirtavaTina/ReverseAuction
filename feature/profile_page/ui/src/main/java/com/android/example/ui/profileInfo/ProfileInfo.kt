package com.android.example.ui.profileInfo

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.android.example.ui.FieldsEnum
import com.android.example.ui.R
import com.android.example.ui.theme.Colors
import com.android.example.ui.theme.robotoFamily
import com.core.common.navigation_constants.ProfilePage
import com.feature.auction.domain.Country
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInfo(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileInfoViewModel = hiltViewModel(),
    role: String
) {

    val profileInfoState by viewModel.state.collectAsStateWithLifecycle()

    val statusBarHeight = with(LocalDensity.current) {
        getStatusBarHeight().toDp()
    }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(Color.White)
//            .padding(top = statusBarHeight)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.chevron_backward),
                contentDescription = "forward icon",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.navigate("${ProfilePage.profile}/${role}") {
                            popUpTo(ProfilePage.info) { inclusive = true }
                        }
                    }
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Profile info", fontFamily = robotoFamily, fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
        }
        Spacer(Modifier.size(20.dp))
        TitleField(id = R.drawable.person, "User Info", Color(0xFF737373), FontWeight.Medium)

        Spacer(modifier = Modifier.size(16.dp))
        Column {
            MyTextField(
                isFocused = profileInfoState.isFocused4,
                title = profileInfoState.firstName,
                changeValue = { newValue ->
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.FIRSTNAME, newValue)
                },
                holderText = "First Name",
                isError = false,
                "Make sure it's not empty.",
                onFocusChanged = { focusState ->
                    viewModel.changeBooleanField(
                        fieldsEnum = FieldsEnum.ISFOCUSED4,
                        focusState.isFocused
                    )
                }
            )
            MyTextField(
                isFocused = profileInfoState.isFocused5,
                title = profileInfoState.lastName,
                changeValue = { newValue ->
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.LASTNAME, newValue)
                },
                holderText = "Surname",
                isError = false,
                "Make sure it's not empty.",
                onFocusChanged = { focusState ->
                    viewModel.changeBooleanField(
                        fieldsEnum = FieldsEnum.ISFOCUSED5,
                        focusState.isFocused
                    )
                }
            )
            MyTextField(
                isFocused = profileInfoState.isFocused6,
                title = profileInfoState.idNumber,
                changeValue = { newValue ->
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.IDNUMBER, newValue)
                },
                holderText = "ID Number",
                isError = false,
                "Make sure it's not empty.",
                onFocusChanged = { focusState ->
                    viewModel.changeBooleanField(
                        fieldsEnum = FieldsEnum.ISFOCUSED6,
                        focusState.isFocused
                    )
                }
            )
//            MyTextField(
//                isFocused = profileInfoState.isFocused7,
//                title = "",
//                changeValue = { newValue ->
////                viewModel.changeStringField(fieldsEnum = FieldsEnum.IDCODE, newValue)
//                },
//                holderText = "Date",
//                isError = false,
//                "Make sure it's not empty.",
//                onFocusChanged = { focusState ->
//                    viewModel.changeBooleanField(
//                        fieldsEnum = FieldsEnum.ISFOCUSED7,
//                        focusState.isFocused
//                    )
//                }
//            )

            DateTimeRow()

            val genders = listOf(
                "Male",
                "Female"
            )

            ExposedDropdownMenuBox(modifier = Modifier.padding(top = 8.dp),
                expanded = profileInfoState.expanded2,
                onExpandedChange = {
                    viewModel.changeBooleanField(
                        fieldsEnum = FieldsEnum.EXPANDED2,
                        !profileInfoState.expanded2
                    )
                }
            ) {
                Column {
                    OutlinedTextField(
                        value = profileInfoState.selectedItem2,
                        onValueChange = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        shape = RoundedCornerShape(12.dp),
                        label = {
                            Text(
                                text = "Gender",
                                fontFamily = robotoFamily,
                                color = Color(0xFF49454F)
                            )
                        },
                        isError = profileInfoState.isSelectionEmpty2,
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
                    if (profileInfoState.isSelectionEmpty2) {
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
                                text = "Gender selection is required.",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                ExposedDropdownMenu(
                    expanded = profileInfoState.expanded2,
                    onDismissRequest = {
                        viewModel.changeBooleanField(fieldsEnum = FieldsEnum.EXPANDED2, false)
                    },
                    modifier = Modifier.background(Color.White)
                ) {
                    genders.forEach { gender ->
                        DropdownMenuItem(
                            text = { Text(gender) },
                            onClick = {
                                viewModel.changeStringField(
                                    fieldsEnum = FieldsEnum.SELECTEDITEM2,
                                    value = gender
                                )
                                viewModel.changeBooleanField(
                                    fieldsEnum = FieldsEnum.ISSELECTIONEMPTY2,
                                    value = false
                                )
                                viewModel.changeBooleanField(
                                    fieldsEnum = FieldsEnum.EXPANDED2,
                                    false
                                )
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(16.dp))

            TitleField(
                id = R.drawable.alternate_email,
                "Contact Info",
                Color(0xFF737373),
                FontWeight.Medium
            )
            Spacer(modifier = Modifier.size(16.dp))
            MyTextField(
                isFocused = profileInfoState.isFocused8,
                title = profileInfoState.email,
                changeValue = { newValue ->
                    viewModel.changeStringField(fieldsEnum = FieldsEnum.EMAIL, newValue)
                },
                holderText = "Email Address",
                isError = false,
                "Make sure it's not empty.",
                onFocusChanged = { focusState ->
                    viewModel.changeBooleanField(
                        fieldsEnum = FieldsEnum.ISFOCUSED8,
                        focusState.isFocused
                    )
                }
            )


            val countries = Country.entries

            Row(verticalAlignment = Alignment.CenterVertically) {
                ExposedDropdownMenuBox(modifier = Modifier.padding(top = 8.dp),
                    expanded = profileInfoState.expanded,
                    onExpandedChange = {
                        viewModel.changeBooleanField(
                            fieldsEnum = FieldsEnum.EXPANDED,
                            !profileInfoState.expanded
                        )
                    }
                ) {
                    Column {
                        OutlinedTextField(
                            value = countries.firstOrNull { it.dialingCode == profileInfoState.selectedItem }?.dialingCode
                                ?: countries[0].dialingCode,
                            onValueChange = { },
                            modifier = Modifier
                                .width(122.dp)
                                .menuAnchor(),
                            readOnly = true,
                            shape = RoundedCornerShape(12.dp, 0.dp, 0.dp, 12.dp),
                            isError = profileInfoState.isSelectionEmpty,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Colors.Black,
                                unfocusedBorderColor = Color(0xFFE8EFF9),
                                focusedLabelColor = Colors.Black,
                                unfocusedLabelColor = Colors.LightGray
                            ),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_drop_down),
                                    null,
                                )
                            }
                        )
                        if (profileInfoState.isSelectionEmpty) {
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
                                    text = "Country selection is required.",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = profileInfoState.expanded,
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
                                text = { Text(country.dialingCode) },
                                onClick = {
                                    viewModel.changeStringField(
                                        fieldsEnum = FieldsEnum.SELECTEDITEM,
                                        value = country.dialingCode
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

                OutlinedTextField(
                    value = profileInfoState.phoneNumber,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                            viewModel.changeStringField(FieldsEnum.PHONENUMBER, newValue)
                        }
                    },
                    label = {
                        Text(
                            text = "Phone Number",
                            fontFamily = robotoFamily,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    isError = false,
                    shape = RoundedCornerShape(0.dp, 12.dp, 12.dp, 0.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Colors.Black,
                        unfocusedBorderColor = Color(0xFFE8EFF9),
                        focusedLabelColor = Colors.Black,
                        unfocusedLabelColor = Colors.LightGray
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Default,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions.Default,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.border_color),
                            null,
                            tint = Color(0xFF3263AB)
                        )
                    },
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            TitleField(
                id = R.drawable.import_contacts,
                "Address Info",
                Color(0xFF737373),
                FontWeight.Medium
            )
            Spacer(modifier = Modifier.size(16.dp))
            TitleField(id = R.drawable.add, "Add Address", Color(0xFF435CB5), FontWeight.Normal)
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun TitleField(id: Int, text: String, color: Color, weight: FontWeight) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = id),
            contentDescription = "forward icon",
            modifier = Modifier
                .padding(start = 3.dp)
                .width(18.dp)
        )
        Text(
            modifier = Modifier.padding(start = 7.dp),
            text = text, fontFamily = robotoFamily, fontWeight = weight,
            fontSize = 14.sp, color = color
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimeRow() {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            DateField()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateField() {
    var date by remember { mutableStateOf(LocalDate.now()) }
    val formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yy"))

    // Show DatePickerDialog on icon click
    val context = LocalContext.current
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            date = LocalDate.of(year, month + 1, dayOfMonth) // Update date with selected value
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth
    )

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = formattedDate,
        onValueChange = { /* Ignore manual input */ },
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) { // Show DatePicker on icon click
                Icon(
                    painter = painterResource(id = R.drawable.event), // Replace with your calendar icon
                    contentDescription = "Calendar",
                    tint = Color(0xFF3263AB)
                )
            }
        },
        label = { Text("Date", color = Color(0xFF3263AB)) },
        shape = RoundedCornerShape(8.dp),
        readOnly = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFE8EFF9),
            unfocusedBorderColor = Color(0xFFE8EFF9),
            focusedLabelColor = Colors.Black,
            unfocusedLabelColor = Colors.LightGray
        ),
    )
}


@Composable
fun MyTextField(
    isFocused: Boolean,
    title: String,
    changeValue: (String) -> Unit,
    holderText: String,
    isError: Boolean,
    errorMessage: String,
    onFocusChanged: (FocusState) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { newValue ->
                changeValue(newValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState)
                },
            isError = isError,
            label = {
                Text(
                    text = holderText,
                    fontFamily = robotoFamily,
                    color = when {
                        isError -> Color.Red
                        isFocused -> Color(0xFF3263AB)
                        else -> Color(0xFF49454F)
                    }
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Colors.Transparent,
                unfocusedContainerColor = Colors.Transparent,
                disabledContainerColor = Colors.Transparent,
                errorBorderColor = Colors.RedError,
                focusedBorderColor = Colors.Black,
                unfocusedBorderColor = Color(0xFFE8EFF9),
                focusedLabelColor = Colors.Black,
                unfocusedLabelColor = Colors.LightGray
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
            keyboardActions = KeyboardActions.Default,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.border_color),
                    null,
                    tint = Color(0xFF3263AB)
                )
            },
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = Colors.RedError,
                fontFamily = robotoFamily,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}


@Composable
fun getStatusBarHeight(): Int {
    val context = LocalContext.current
    val rootView = (context as? Activity)?.window?.decorView ?: return 0
    val insets = ViewCompat.getRootWindowInsets(rootView)
    return insets?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: 0
}


@Preview(showBackground = true)
@Composable
fun InfoPreview() {
//    ProfileInfo()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateInputField(
    isFocused: Boolean,
//    title: String,
    changeValue: (String) -> Unit,
    isError: Boolean,
//    errorMessage: String,
    onFocusChanged: (FocusState) -> Unit,
    value: String,
//    onDateChange: (String) -> Unit,
//    formattedDate: String
) {

    val viewModel: ProfileInfoViewModel = hiltViewModel()
    val profileInfoState by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        OutlinedTextField(
            value = profileInfoState.formattedDate,
            onValueChange = { input ->
                if (input.all { it.isDigit() } || input.isEmpty()) {
                    changeValue(input)
                }
            },
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState)
                },
            isError = isError,
            label = {
                Text(
                    text = "Date",
                    fontFamily = robotoFamily,
                    color = when {
                        isError -> Color.Red
                        isFocused -> Color(0xFF3263AB)
                        else -> Color(0xFF49454F)
                    }
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Colors.Transparent,
                unfocusedContainerColor = Colors.Transparent,
                disabledContainerColor = Colors.Transparent,
                errorBorderColor = Colors.RedError,
                focusedBorderColor = Colors.Black,
                unfocusedBorderColor = Color(0xFFE8EFF9),
                focusedLabelColor = Colors.Black,
                unfocusedLabelColor = Colors.LightGray
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),

//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
//            keyboardActions = KeyboardActions.Default,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.border_color),
                    null,
                    tint = Color(0xFF3263AB)
                )
            },
            singleLine = true
        )
    }
}
