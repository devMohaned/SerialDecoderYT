package com.serial.decoder.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isDeniedPermanently(): Boolean {
    return !hasPermission && !shouldShowRationale
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionState.HandlePermissionCases(
    modifier: Modifier = Modifier,
    ShouldShowRationaleContent: @Composable() (modifier: Modifier) -> Unit, // Denied Once
    PermissionDeniedPermanentlyContent: @Composable() (modifier: Modifier) -> Unit, // Denied Twice/Permenant
    HasPermissionContent: @Composable() (modifier: Modifier) -> Unit, // Accepted
) {
    when {
        hasPermission -> {
            HasPermissionContent.invoke(modifier)
        }
        shouldShowRationale -> {
            ShouldShowRationaleContent.invoke(modifier)
        }
        isDeniedPermanently() -> {
            PermissionDeniedPermanentlyContent.invoke(modifier)
        }
    }
}
