package com.android.example.ui.auctions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.android.example.domain.model.Category
import com.android.example.ui.R
import com.android.example.ui.theme.Colors
import com.android.example.ui.theme.robotoFamily
import com.core.common.navigation_constants.AuthFeature
import com.core.common.navigation_constants.HomePage
import kotlinx.coroutines.launch

@Composable
fun AddAuction(
    modifier: Modifier = Modifier, viewModel: AuctionsViewModel = hiltViewModel(), navController: NavController
) {
    val auctionsState by viewModel.state.collectAsStateWithLifecycle()


//    val statusBarHeight = with(LocalDensity.current) {
//        getStatusBarHeight().toDp()
//    }

    Column(
        modifier = modifier
//            .padding(top = statusBarHeight)
            .fillMaxSize()
            .background(Color.White)
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
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.navigate("${ HomePage.homePage }/Buyer") {
                            popUpTo(HomePage.addAction) { inclusive = true }
                        }
                    })
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Add New Auction", fontFamily = robotoFamily, fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
        }
        Spacer(Modifier.size(24.dp))

        Column(Modifier.padding(horizontal = 20.dp)) {
            Text(
                "Product Info",
                fontFamily = robotoFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF737373)
            )
//        MyTextField(false, "Title", {}, "Title", )
            MyTextField(
                isFocused = auctionsState.isFocused,
                title = auctionsState.title,
                changeValue = { newValue ->
                    viewModel.changeStringField(auctionsEnum = AuctionsEnum.TITLE, newValue)
                },
                holderText = "Title",
                isError = false,
                "Make sure it's not empty.",
                onFocusChanged = { focusState ->
                    viewModel.changeBooleanField(
                        auctionsEnum = AuctionsEnum.ISFOCUSED,
                        focusState.isFocused
                    )
                }
            )
            Categories(
                auctionsState = auctionsState,
                onDismiss = { viewModel.changeBooleanField(AuctionsEnum.EXPANDCATEGORY, false) },
                onExpandedChange = {
                    viewModel.changeBooleanField(
                        AuctionsEnum.EXPANDCATEGORY,
                        !auctionsState.expandCategory
                    )
                },
                onSelectCategory = { category ->
                    viewModel.changeStringField(
                        AuctionsEnum.SELECTCATEGORY,
                        category.categoryName ?: ""
                    )
                    viewModel.changeBooleanField(AuctionsEnum.EXPANDCATEGORY, false)
//                    auctionsState.selectedCategoryId = category.parentCategoryId
                    category.id?.let { viewModel.fetchSubCategories(categoryId = it) }
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            SubCategories(
                auctionsState = auctionsState,
                onDismiss = { viewModel.changeBooleanField(AuctionsEnum.EXPANDSUBCATEGORY, false) },
                onExpandedChange = {
                    viewModel.changeBooleanField(
                        AuctionsEnum.EXPANDSUBCATEGORY,
                        !auctionsState.expandSubcategory
                    )
                },
                onCategorySelected = { subCategoryName ->
                    viewModel.changeStringField(
                        AuctionsEnum.SELECTSUBCATEGORY,
                        subCategoryName
                    ) // Set name instead of ID
                    viewModel.changeBooleanField(AuctionsEnum.EXPANDSUBCATEGORY, false)
                },
            )
            Spacer(Modifier.size(20.dp))
            RichTextEditor(
                auctionsState = auctionsState,
                onValueChange = { newValue ->
                    viewModel.changeStringField(
                        AuctionsEnum.DESCRIPTION,
                        newValue
                    )
                }
            )
            Spacer(Modifier.size(24.dp))
            MaximumBudget(auctionsState, viewModel)
            Spacer(Modifier.size(24.dp))
            Button(
                onClick = {
                },
                modifier = Modifier
                    .padding(top = 15.dp,bottom = 15.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFF3263AB),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF3263AB),
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    text = "Next",
                    fontSize = 14.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(
    auctionsState: AuctionsState,
    onDismiss: () -> Unit,
    onExpandedChange: () -> Unit,
    onSelectCategory: (Category) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = auctionsState.expandCategory,
        onExpandedChange = {
            onExpandedChange()
        }
    ) {
        Column {
            OutlinedTextField(
                value = auctionsState.selectCategory,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                shape = RoundedCornerShape(12.dp),
                label = {
                    Text(
                        text = "Category",
                        fontFamily = robotoFamily,
                        color = Color(0xFF49454F)
                    )
                },
                isError = auctionsState.isCategoryEmpty,
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
//            if (auctionsState.isCategoryEmpty) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .padding(start = 6.dp)
//                        .padding(top = 2.dp)
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.error),
//                        null,
//                        tint = Color.Red,
//                        modifier = Modifier
//                            .size(12.dp)
//                    )
//                    Spacer(modifier = Modifier.size(10.dp))
//                    Text(
//                        text = "Country selection is required.",
//                        color = Color.Red,
//                        fontSize = 12.sp,
//                        modifier = Modifier.padding(top = 2.dp)
//                    )
//                }
//            }
        }

        ExposedDropdownMenu(
            expanded = auctionsState.expandCategory,
            onDismissRequest = onDismiss,
            modifier = Modifier.background(Color.White)
        ) {
            auctionsState.categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(category.categoryName ?: "Unknown")
                    },
                    onClick = {
                        onSelectCategory(category) // Pass the whole category object
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubCategories(
    auctionsState: AuctionsState,
    onDismiss: () -> Unit,
    onExpandedChange: () -> Unit,
    onCategorySelected: (String) -> Unit // Accepting subcategory name as String
) {
    ExposedDropdownMenuBox(
        expanded = auctionsState.expandSubcategory,
        onExpandedChange = { onExpandedChange() }
    ) {
        Column {
            OutlinedTextField(
                enabled = auctionsState.subCategories.isNotEmpty(),
                value = auctionsState.selectSubcategory,  // Now this should hold the subcategory name
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                shape = RoundedCornerShape(12.dp),
                label = {
                    Text(
                        text = "Subcategory",
                        fontFamily = robotoFamily,
                        color = if (auctionsState.subCategories.isNotEmpty()) (Color(0xFF49454F)) else Color(
                            0xFF9E9E9E
                        )
                    )
                },
                isError = auctionsState.isCategoryEmpty,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Colors.Black,
                    unfocusedBorderColor = Color(0xFFE8EFF9),
                    focusedLabelColor = Colors.Black,
                    unfocusedLabelColor = Colors.LightGray
                ),
            )
        }

        if (auctionsState.subCategories.isNotEmpty())
            ExposedDropdownMenu(
                expanded = auctionsState.expandSubcategory,
                onDismissRequest = onDismiss,
                modifier = Modifier.background(Color.White)
            ) {
                auctionsState.subCategories.forEach { subCategory ->
                    DropdownMenuItem(
                        text = {
                            if (subCategory != null) {
                                Text(subCategory)
                            }
                        },
                        onClick = {
                            if (subCategory != null) {
                                onCategorySelected(subCategory)
                            }
                        }
                    )
                }
            }
    }
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

//@Composable
//fun getStatusBarHeight(): Int {
//    val context = LocalContext.current
//    val rootView = (context as? Activity)?.window?.decorView ?: return 0
//    val insets = ViewCompat.getRootWindowInsets(rootView)
//    return insets?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: 0
//}

@Composable
fun RichTextEditor(
    auctionsState: AuctionsState,
    onValueChange: (String) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(188.dp)
            .background(Color(0xFFFFFFFF), RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFE8EFF9), RoundedCornerShape(16.dp))
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.format_bold),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF9E9E9E)
            )
            Spacer(Modifier.size(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.format_italic),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF9E9E9E)
            )
            Spacer(Modifier.size(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.format_underlined),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF9E9E9E)
            )
            Spacer(Modifier.size(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.line),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF9E9E9E)
            )
            Spacer(Modifier.size(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.format_list_bulleted),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF9E9E9E)
            )
            Spacer(Modifier.size(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.format_list_numbered),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF9E9E9E)
            )
            Spacer(Modifier.size(12.dp))
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = Color(0xFFE8EFF9)
        )
        OutlinedTextField(
            value = auctionsState.description,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(0xFF5A5A5A), // Set text color here
                fontSize = 14.sp // Set font size here
            ),
            placeholder = {
                Text(
                    text = "Add Product Information ...",
                    fontFamily = robotoFamily,
                    color = Color.Gray // Optional: Set placeholder color
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent, // Transparent focused border
                unfocusedBorderColor = Color.Transparent, // Transparent unfocused border
                focusedLabelColor = Color.Black, // Label color when focused
                unfocusedLabelColor = Color.LightGray // Label color when not focused
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text // Text input keyboard
            )
        )
    }
}

@Composable
fun MaximumBudget(auctionsState: AuctionsState, viewModel: AuctionsViewModel) {
    Column(
        Modifier
            .fillMaxWidth()
//            .height(210.dp)
            .background(Color(0xFFF7F9FD), RoundedCornerShape(16.dp))
    ) {
        Text(
            "Indicate the maximum budget",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp, bottom = 16.dp),
            fontFamily = robotoFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF737373)
        )
        Row(Modifier.padding(horizontal = 16.dp)) {
            OutlinedTextField(
                value = auctionsState.budget,
                onValueChange = { newValue ->
                    viewModel.changeStringField(
                        AuctionsEnum.BUDGET,
                        newValue
                    )
                },
                modifier = Modifier
                    .width(220.dp)
//                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent, // Transparent focused border
                    unfocusedBorderColor = Color.Transparent, // Transparent unfocused border
                    focusedLabelColor = Color.Black, // Label color when focused
                    unfocusedLabelColor = Color.LightGray // Label color when not focused
                )
            )
            Spacer(modifier = Modifier.size(12.dp))
            Currency(
                auctionsState = auctionsState,
                onExpandedChange = { isExpanded ->
                    viewModel.changeBooleanField(
                        auctionsEnum = AuctionsEnum.CURRENCYEXPANDED,
                        isExpanded
                    )
                },
                onDismissRequest = {
                    viewModel.changeBooleanField(AuctionsEnum.CURRENCYEXPANDED, false)
                },
                onSelectCurrency = { selectedCurrency ->
                    viewModel.changeStringField(
                        auctionsEnum = AuctionsEnum.CURRENCY,
                        value = selectedCurrency
                    )
                }
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            thickness = 1.dp,
            color = Color(0xFFE8EFF9)
        )
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Checkbox(
                checked = auctionsState.isChecked,
                onCheckedChange = {
                    viewModel.changeBooleanField(
                        AuctionsEnum.ISCHECKED,
                        !auctionsState.isChecked
                    )
                },
                modifier = Modifier.padding(end = 8.dp)
            )

            Column {
                Text(
                    text = "Budget isn’t specified",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = robotoFamily,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "If you don't have a specified budget, you can indicate the “price negotiable.”",
                    fontSize = 11.sp,
                    color = Color.Black,
                    fontFamily = robotoFamily,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Currency(
    auctionsState: AuctionsState,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    onSelectCurrency: (String) -> Unit // Changed to take the selected currency as a parameter
) {
    val currencies = listOf(
        "AMD", "AZN", "CNY", "EUR", "GEL", "KZT", "RUB", "TRY", "USD"
    )

    ExposedDropdownMenuBox(
        modifier = Modifier,
        expanded = auctionsState.currencyExpanded,
        onExpandedChange = { onExpandedChange(!auctionsState.currencyExpanded) } // Toggle expanded state
    ) {
        Column {
            OutlinedTextField(
                value = auctionsState.currency,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .menuAnchor(),
                readOnly = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Colors.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedLabelColor = Colors.Black,
                    unfocusedLabelColor = Colors.LightGray
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_down),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        }

        ExposedDropdownMenu(
            expanded = auctionsState.currencyExpanded,
            onDismissRequest = {
                onDismissRequest()
            },
            modifier = Modifier.background(Color.White)
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(currency) },
                    onClick = {
                        onSelectCurrency(currency) // Call to pass selected currency
                        onDismissRequest() // Dismiss the dropdown
                    }
                )
            }
        }
    }
}


@Composable
@Preview
fun AddAuctionPreview() {
//    MaximumBudget()
}