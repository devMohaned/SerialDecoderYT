package com.serial.decoder.feature_decoding.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.serial.decoder.R
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.serial.decoder.core.*
import com.serial.decoder.core.util.*
import com.serial.decoder.feature_decoding.data.local.util.Brands
import kotlinx.coroutines.flow.collectLatest

object HomeDeepLinking {
    private const val SCHEME = "HTTPS"
    private const val HOST = "www.example.com"
    const val ARG_1_SERIAL = "serial"
    const val ARG_2_BRAND = "brand"
    private const val ARGS = "$ARG_1_SERIAL={$ARG_1_SERIAL}&$ARG_2_BRAND={$ARG_2_BRAND}"
    const val URI = "$SCHEME://$HOST?$ARGS"
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    serial: String? = null,
    brand: String? = null,
    onFAQActionPressed: () -> Unit
) {
    val serialWithBrand = viewModel.serialWithBrand.collectAsState(initial = listOf("", ""))
    val uiState = viewModel.uiState.collectAsState()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val sheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val shouldOpenDialog = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = viewModel.uiEvent) {

        val isDeepLink =
            serial != null && serial.isNotEmpty()
                    && brand != null && brand.isNotEmpty()
                    && (Brands.values().any { it.name == brand })

        if (isDeepLink) {
            viewModel.updateSerial(serial!!)
            viewModel.updateBrand(Brands.valueOf(brand!!))
        }

        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is HomeUIEvent.ShowBrandsDialog -> shouldOpenDialog.value = true
                is HomeUIEvent.ShowSnackBar -> {
                    sheetScaffoldState.snackbarHostState.showSnackbar(
                        message = event.text,
                    )
                }
            }

        }
    }

    if (shouldOpenDialog.value) {
        Dialog(
            brands = viewModel.brands,
            onBrandClicked = { selectedBrand ->
                viewModel.updateBrand(selectedBrand)
            },
            onDialogDismissed = {
                shouldOpenDialog.value = false
            })
    }

    BottomSheetScaffold(
        scaffoldState = sheetScaffoldState,
        topBar = {
            AppBar(
                modifier,
                serialWithBrand.value[BRAND_INDEX],
                { viewModel.showDialog() },
                onFAQActionPressed
            )
        },
        sheetElevation = ZERO_DP,
        sheetPeekHeight = BOTTOM_ICON_CONTAINER_SIZE,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            val context = LocalContext.current
            BottomSheetContent(modifier, sheetState, onBarcodeDetected = { barcodes ->
                barcodes.forEach { barcode ->
                    barcode.rawValue?.let { barcodeValue ->
                        viewModel.updateSerial(barcodeValue)
//                        Toast.makeText(context, barcodeValue, Toast.LENGTH_SHORT).show()
                    }
                }
            },
                onBarcodeFailed = {
                    viewModel.showSnackBar(
                        UIText.StringResource(
                            resId = R.string.barcode_error,
                            it.localizedMessage?.toString() ?: ""
                        ).asString(context)
                    )
                },
                onBarcodeNotFound = {
                    // Do Nothing!
                })


        }
    ) {
        HomeContent(modifier, uiState.value, serialWithBrand.value[SERIAL_INDEX]) { newSerial ->
            viewModel.updateSerial(
                newSerial
            )
        }
    }
}


@Composable
fun AppBar(
    modifier: Modifier = Modifier, title: String, OnChangeManufactureActionClicked: () -> Unit,
    OnFAQActionClicked: () -> Unit
) {
    TopAppBar(modifier = modifier, backgroundColor = MaterialTheme.colors.surface, title = {
        AppBarTitle(modifier = modifier, title = title)
    }, actions = {
        AppBarMenuItems(modifier, OnChangeManufactureActionClicked, OnFAQActionClicked)
    }, navigationIcon = {
        AppBarIcon()

    })
}

@Composable
fun AppBarIcon(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier.padding(start = SPACING_NORMAL),
        painter = painterResource(id = R.drawable.ic_scanner), contentDescription = stringResource(
            id = R.string.scanner_icon
        ), tint = Color.Unspecified
    )
}

@Composable
fun AppBarTitle(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.serial_decoder)
) {
    Text(
        text = title,
        modifier = modifier.testTag("AppTitle"),
        style = MaterialTheme.typography.h3
    )
}

@Composable
fun AppBarMenuItems(
    modifier: Modifier = Modifier,
    OnChangeManufactureActionClicked: () -> Unit,
    OnFAQActionClicked: () -> Unit
) {
    Row(modifier = modifier) {
        MenuItem(
            imageResourceId = R.drawable.ic_apple,
            contentDescriptionId = R.string.change_manufactor,
            onClick = OnChangeManufactureActionClicked
        )
        MenuItem(
            imageResourceId = R.drawable.ic_info,
            contentDescriptionId = R.string.faq_button,
            onClick = OnFAQActionClicked
        )
    }
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    @DrawableRes imageResourceId: Int,
    @StringRes contentDescriptionId: Int,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painter = painterResource(id = imageResourceId),
            contentDescription = stringResource(id = contentDescriptionId),
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    homeUIState: HomeUIState,
    serial: String,
    onSerialChanged: (serial: String) -> Unit,
) {
    if (homeUIState.isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        ResultContent(modifier, homeUIState, serial, onSerialChanged)
    }
}


@Composable
fun ResultContent(
    modifier: Modifier = Modifier,
    homeUIState: HomeUIState,
    serial: String,
    onSerialChanged: (serial: String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ManufacturedElement(
            imageRes = R.drawable.ic_manufactored_type,
            contentDescriptionId = R.string.manufactored_type_image,
            text = homeUIState.manufacture.type.asString()
        )

        ManufacturedElement(
            imageRes = R.drawable.ic_manufactor_location,
            contentDescriptionId = R.string.manufactored_location_image,
            text = stringResource(id = R.string.made_in, homeUIState.manufacture.country.asString())
        )

        ManufacturedElement(
            imageRes = R.drawable.ic_manufactored_date,
            contentDescriptionId = R.string.manufactored_date_image,
            text = homeUIState.manufacture.date.asString()
        )

        SerialNumberTextField(
            modifier
                .padding(horizontal = SPACING_DOUBLE)
                .fillMaxWidth(), serial, onValueChange = onSerialChanged
        )
    }
}

@Composable
fun ManufacturedElement(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
    @DrawableRes imageRes: Int,
    @StringRes contentDescriptionId: Int,
    text: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SPACING_NORMAL),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        ManufacturedImage(
            modifier = modifier.weight(0.3f),
            imageRes = imageRes,
            contentDescriptionId = contentDescriptionId
        )
        ManufacturedText(
            modifier = modifier
                .weight(0.6f),
            text = text
        )
    }
}

@Composable
fun ManufacturedImage(
    modifier: Modifier = Modifier, @DrawableRes imageRes: Int,
    @StringRes contentDescriptionId: Int,
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = imageRes),
        contentDescription = stringResource(
            id = contentDescriptionId
        )
    )
}

@Composable
fun ManufacturedText(
    modifier: Modifier = Modifier, text: String
) {

    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.h2, textAlign = TextAlign.Center
    )

}

@Composable
fun SerialNumberTextField(
    modifier: Modifier = Modifier,
    title: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = title,
        onValueChange = onValueChange,
        singleLine = true,
        label = {
            Text(text = stringResource(id = R.string.insert_serial_number))
        })
}
