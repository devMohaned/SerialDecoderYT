package com.serial.decoder.core.nav

import android.content.Intent
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.serial.decoder.feature_decoding.ui.HomeDeepLinking
import com.serial.decoder.feature_decoding.ui.HomeDeepLinking.ARG_1_SERIAL
import com.serial.decoder.feature_decoding.ui.HomeDeepLinking.ARG_2_BRAND
import com.serial.decoder.feature_decoding.ui.HomeScreen
import com.serial.decoder.feature_faq.ui.FrequentlyAskedQuestionsScreen
import com.serial.decoder.feature_onboarding.ui.OnBoardingScreen


@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    isFirstTime: Boolean = true,
    navController: NavHostController =  rememberNavController(),
    windowSize: WindowWidthSizeClass
) {

    val startDestination =
        if (isFirstTime) NavigationGraph.OnBoarding.name else NavigationGraph.Home.name

    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavigationGraph.OnBoarding.name) {
            OnBoardingScreen(windowWidthSizeClass = windowSize, OnLaunchedBefore = {
                navController.navigate(NavigationGraph.Home.name) {
                    popUpTo(NavigationGraph.OnBoarding.name) { inclusive = true }
                }
            })
        }

        composable(
            NavigationGraph.Home.name,
            deepLinks = listOf(navDeepLink {
                // uri: https://www.example.com?serial=07953NEM600767H&brand=SAMSUNG
                uriPattern = HomeDeepLinking.URI
                action = Intent.ACTION_VIEW
            }),
            arguments = listOf(navArgument(ARG_1_SERIAL) {
                type = NavType.StringType
                nullable = true
            },
                navArgument(ARG_2_BRAND) {
                    type = NavType.StringType
                    nullable = true
                })
        ) {
            val serial = it.arguments?.getString(ARG_1_SERIAL)
            val brand = it.arguments?.getString(ARG_2_BRAND)
            HomeScreen(serial = serial, brand = brand, onFAQActionPressed = {
                navController.navigate(NavigationGraph.FAQ.name)
            })
        }
        composable(NavigationGraph.FAQ.name) {
            FrequentlyAskedQuestionsScreen(onBackButtonPressed = { navController.popBackStack() })
        }
    }
}