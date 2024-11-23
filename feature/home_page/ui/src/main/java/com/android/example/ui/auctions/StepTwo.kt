package com.android.example.ui.auctions

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.example.ui.R
import com.android.example.ui.theme.Colors
import com.android.example.ui.theme.robotoFamily
import com.feature.auction.domain.Country
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun AuctionDetails(
//    modifier: Modifier = Modifier,
////    auctionsState: AuctionsState,
//    viewModel: AuctionsViewModel = hiltViewModel()
//) {
//    val auctionsState by viewModel.state.collectAsStateWithLifecycle()
//
//    Column(
//        modifier = modifier
////            .padding(top = statusBarHeight)
//            .fillMaxSize()
//            .background(Color.White)
//            .verticalScroll(rememberScrollState())
//    ) {
//        DateTimePickerRow()
//        Spacer(modifier = Modifier.size(36.dp))
//        PhoneNumber(auctionsState, viewModel)
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumber(auctionsState: AuctionsState, viewModel: AuctionsViewModel) {
    val countries = Country.entries

    Row(verticalAlignment = Alignment.CenterVertically) {
        ExposedDropdownMenuBox(modifier = Modifier.padding(top = 8.dp),
            expanded = auctionsState.expanded,
            onExpandedChange = {
                viewModel.changeBooleanField(
                    auctionsEnum = AuctionsEnum.EXPANDED,
                    !auctionsState.expanded
                )
            }
        ) {
            Column {
                OutlinedTextField(
                    value = countries.firstOrNull { it.dialingCode == auctionsState.selectedItem }?.dialingCode
                        ?: countries[0].dialingCode,
                    onValueChange = { },
                    modifier = Modifier
                        .width(122.dp)
                        .menuAnchor(),
                    readOnly = true,
                    shape = RoundedCornerShape(12.dp, 0.dp, 0.dp, 12.dp),
                    isError = auctionsState.isSelectionEmpty,
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
                if (auctionsState.isSelectionEmpty) {
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
                expanded = auctionsState.expanded,
                onDismissRequest = {
                    viewModel.changeBooleanField(
                        AuctionsEnum.EXPANDED,
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
                                AuctionsEnum.SELECTEDITEM,
                                value = country.dialingCode
                            )
                            viewModel.changeBooleanField(
                                AuctionsEnum.ISSELECTIONEMPTY,
                                value = false
                            )
                            viewModel.changeBooleanField(
                                AuctionsEnum.EXPANDED,
                                false
                            )
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = auctionsState.phoneNumber,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                    viewModel.changeStringField(AuctionsEnum.PHONENUMBER, newValue)
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
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DateTimePickerRow() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        DateTimeRow(label = "Start Time:")
        Spacer(modifier = Modifier.height(16.dp))
        DateTimeRow(label = "End Time:")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimeRow(label: String) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            fontFamily = robotoFamily
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            DateField()
            Spacer(modifier = Modifier.width(8.dp))
            TimeField()
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeField() {
    var time by remember {
        mutableStateOf(
            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        )
    }


    OutlinedTextField(
        value = time,
        onValueChange = { /* Ignore manual input */ },
        label = { Text("Time", color = Color(0xFF3263AB)) },
        shape = RoundedCornerShape(8.dp),
        readOnly = true,
        modifier = Modifier.width(100.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFE8EFF9),
            unfocusedBorderColor = Color(0xFFE8EFF9),
            focusedLabelColor = Colors.Black,
            unfocusedLabelColor = Colors.LightGray
        ),
    )
}
