package com.serial.decoder.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.serial.decoder.core.nav.NavigationHost
import com.serial.decoder.core.ui.MainViewModel
import com.serial.decoder.feature_onboarding.ui.OnBoardingScreen
import com.serial.decoder.ui.theme.SerialDecoderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SerialDecoderTheme {
                val windowSize = calculateWindowSizeClass(this)
                SerialDecoderApp(windowSize.widthSizeClass)
            }
        }
    }
}


@Composable
fun SerialDecoderApp(
    windowSize: WindowWidthSizeClass,
    mainViewModel: MainViewModel = hiltViewModel<MainViewModel>(),
    navController: NavHostController = rememberNavController()
) {
    NavigationHost(
        windowSize = windowSize,
        isFirstTime = mainViewModel.isFirstTime,
        navController = navController
    )
}