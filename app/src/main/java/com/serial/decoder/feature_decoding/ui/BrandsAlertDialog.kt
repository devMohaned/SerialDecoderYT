package com.serial.decoder.feature_decoding.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serial.decoder.R
import com.serial.decoder.core.ui.NormalButton
import com.serial.decoder.core.util.SPACING_DOUBLE
import com.serial.decoder.core.util.SPACING_NORMAL
import com.serial.decoder.core.util.SPACING_QUADRUPLE
import com.serial.decoder.feature_decoding.data.local.util.Brands
import com.serial.decoder.ui.theme.NORMAL_CORNER_DP


@Composable
fun Dialog(
    modifier: Modifier = Modifier,
    onBrandClicked: (brand: Brands) -> Unit,
    brands: Array<Brands>,
    onDialogDismissed: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDialogDismissed,
        title = {
            Text(
                text = stringResource(id = R.string.choose_a_brand),
                style = MaterialTheme.typography.h3
            )
        },
        text = {
            LazyColumn() {
                items(brands.size) { index ->
                    Brand(
                        modifier = modifier,
                        brand = brands[index],
                        onBrandClicked = onBrandClicked,
                        onDialogDismissed = onDialogDismissed
                    )
                }
            }
        },
        buttons = {
            OutlinedButton(
                modifier = modifier
                    .padding(horizontal = SPACING_DOUBLE)
                    .padding(bottom = SPACING_DOUBLE)
                    .fillMaxWidth(),
                onClick = onDialogDismissed,
                shape = RoundedCornerShape(NORMAL_CORNER_DP)
            ) {
                Text(stringResource(id = R.string.dismiss))
            }
        }
    )
}

@Composable
fun Brand(
    modifier: Modifier = Modifier,
    brand: Brands,
    onBrandClicked: (brand: Brands) -> Unit,
    onDialogDismissed: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = SPACING_NORMAL), onClick = {
            onBrandClicked(brand)
            onDialogDismissed()
        }, shape = RoundedCornerShape(NORMAL_CORNER_DP)
    ) {
        Text(
            text = brand.name
        )
    }


}