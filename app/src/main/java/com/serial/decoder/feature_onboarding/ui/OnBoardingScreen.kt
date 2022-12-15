package com.serial.decoder.feature_onboarding.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.whenStarted
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.serial.decoder.R
import com.serial.decoder.core.*
import com.serial.decoder.core.ui.NormalButton
import com.serial.decoder.core.util.*
import com.serial.decoder.ui.theme.ButtonCornerShape
import com.serial.decoder.ui.theme.SerialDecoderTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val FIRST_PAGE_INDEX = 0
const val SECOND_PAGE_INDEX = 1
const val THIRD_PAGE_INDEX = 2
lateinit var dotsType: OnBoardingScrollingType

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    windowWidthSizeClass: WindowWidthSizeClass,
    OnLaunchedBefore: () -> Unit
) {
    dotsType = when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            OnBoardingScrollingType.BOTTOM_DOTS
        }
        WindowWidthSizeClass.Medium -> {
            OnBoardingScrollingType.BOTTOM_DOTS
        }
        WindowWidthSizeClass.Expanded -> {
            OnBoardingScrollingType.NO_DOTS
        }
        else -> {
            OnBoardingScrollingType.BOTTOM_DOTS
        }
    }

    OnBoardScreen(dotsType = dotsType, modifier = modifier, OnLaunchedBefore = OnLaunchedBefore)
}

@Composable
fun OnBoardScreen(
    modifier: Modifier = Modifier, dotsType: OnBoardingScrollingType,
    viewModel: OnBoardingViewModel = hiltViewModel<OnBoardingViewModel>(),
    OnLaunchedBefore: () -> Unit
) {
    // Use the 'viewModel()' function from the lifecycle-viewmodel-compose artifact
    val uiState by viewModel.uiState.collectAsState()
    if (dotsType == OnBoardingScrollingType.BOTTOM_DOTS)
        HorizontalOnBoardingScreenContent(
            modifier = modifier,
            enabled = !uiState.isLoading,
            onGetStartedClicked = {
                getStarted(viewModel)
            })
    else
        VerticalOnBoardingScreenContent(
            modifier = modifier,
            onGetStartedClicked = {
                getStarted(viewModel)
            },
            isLoading = uiState.isLoading
        )

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(key1 = true) {
        lifecycle.whenStarted {
            launch {
                viewModel.uiEvent.collectLatest {
                    when (it) {
                        is OnBoardingUIEvent.UpdateFirstLaunch -> OnLaunchedBefore()
                    }
                }
            }
        }
    }
}

private fun getStarted(viewModel: OnBoardingViewModel) {
    viewModel.updateFirstLaunch()
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalOnBoardingScreenContent(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onGetStartedClicked: () -> Unit
) {
    val state = rememberPagerState()
    HorizontalPager(count = 3, state = state) { page ->
        when (page) {
            FIRST_PAGE_INDEX -> {
                OnBoardingScreenContent(
                    modifier = modifier,
                    image = R.drawable.onboarding_image_one,
                    contentDescription = R.string.onboarding_select_brand_image,
                    headerText = R.string.select_a_brand,
                    subtitleText = R.string.onboarding_message_one,
                    selectedPage = FIRST_PAGE_INDEX,
                    dotsType = dotsType
                )
            }

            SECOND_PAGE_INDEX -> {
                OnBoardingScreenContent(
                    modifier = modifier,
                    image = R.drawable.onboarding_image_two,
                    contentDescription = R.string.onboarding_insert_serial_number_image,
                    headerText = R.string.insert_serial_number,
                    subtitleText = R.string.onboarding_message_two,
                    selectedPage = SECOND_PAGE_INDEX,
                    dotsType = dotsType
                )
            }

            THIRD_PAGE_INDEX -> {
                OnBoardingScreenContent(
                    modifier = modifier,
                    image = R.drawable.onboarding_image_three,
                    contentDescription = R.string.onboarding_view_details_about_device_image,
                    headerText = R.string.view_and_read,
                    subtitleText = R.string.onboarding_message_three,
                    selectedPage = THIRD_PAGE_INDEX,
                    dotsType = dotsType,
                    enabled = enabled,
                    onGetStartedClicked = onGetStartedClicked
                )
            }
        }
    }
}

@Composable
fun VerticalOnBoardingScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onGetStartedClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        OnBoardingScreenContent(
            modifier = modifier,
            image = R.drawable.onboarding_image_one,
            contentDescription = R.string.onboarding_select_brand_image,
            headerText = R.string.select_a_brand,
            subtitleText = R.string.onboarding_message_one,
            selectedPage = FIRST_PAGE_INDEX,
            dotsType = dotsType
        )
        Divider(modifier = modifier.padding(vertical = SPACING_NORMAL))

        OnBoardingScreenContent(
            modifier = modifier,
            image = R.drawable.onboarding_image_two,
            contentDescription = R.string.onboarding_insert_serial_number_image,
            headerText = R.string.insert_serial_number,
            subtitleText = R.string.onboarding_message_two,
            selectedPage = SECOND_PAGE_INDEX,
            dotsType = dotsType
        )
        Divider(modifier = modifier.padding(vertical = SPACING_NORMAL))
        OnBoardingScreenContent(
            modifier = modifier,
            image = R.drawable.onboarding_image_three,
            contentDescription = R.string.onboarding_view_details_about_device_image,
            headerText = R.string.view_and_read,
            subtitleText = R.string.onboarding_message_three,
            selectedPage = THIRD_PAGE_INDEX,
            dotsType = dotsType,
        )
        GetStartedButton(modifier = modifier, enabled = !isLoading, onClick = onGetStartedClicked)

    }
}


@Composable
fun OnBoardingScreenContent(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    @StringRes contentDescription: Int,
    @StringRes headerText: Int,
    @StringRes subtitleText: Int,
    selectedPage: Int = FIRST_PAGE_INDEX,
    dotsType: OnBoardingScrollingType,
    enabled: Boolean = true,
    onGetStartedClicked: () -> Unit = {}
) {
    if (dotsType == OnBoardingScrollingType.BOTTOM_DOTS) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ImageContent(
                modifier = modifier,
                drawableRes = image,
                contentDescriptionRes = contentDescription
            )
            OnBoardingHeader(modifier = modifier, textRes = headerText)
            OnBoardingSubTitle(modifier = modifier, textRes = subtitleText)
            Spacer(modifier = modifier.weight(1f))

            if (selectedPage == THIRD_PAGE_INDEX)
                GetStartedButton(
                    modifier = modifier,
                    enabled = enabled,
                    onClick = onGetStartedClicked
                )
            else
                NavigationDots(
                    modifier = modifier,
                    dotsType = dotsType,
                    currentPage = selectedPage,
                )
        }
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageContent(
                modifier = modifier,
                drawableRes = image,
                contentDescriptionRes = contentDescription, paddingRatio = 0.0f,
                ONBOARDING_IMAGE_SIZE
            )
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnBoardingHeader(modifier = modifier, textRes = headerText, paddingRatio = 0f)
                OnBoardingSubTitle(modifier = modifier, textRes = subtitleText)
            }

        }
    }
}


@Composable
fun ImageContent(
    modifier: Modifier = Modifier,
    @DrawableRes drawableRes: Int,
    @StringRes contentDescriptionRes: Int,
    paddingRatio: Float = 1f,
    size: Dp = ONBOARDING_IMAGE_SIZE
) {
    Image(
        painter = painterResource(id = drawableRes),
        contentDescription = stringResource(
            id = contentDescriptionRes
        ),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .padding(
                top = paddingRatio * SPACING_LARGE,
                start = SPACING_DOUBLE,
                end = SPACING_DOUBLE
            )
            .size(size = size),
    )
}

@Composable
fun OnBoardingHeader(
    modifier: Modifier = Modifier, @StringRes textRes: Int, paddingRatio: Float = 1f
) {
    Text(
        text = stringResource(id = textRes),
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = SPACING_QUADRUPLE * paddingRatio,
                bottom = SPACING_DOUBLE * paddingRatio,
            )
    )
}

@Composable
fun OnBoardingSubTitle(modifier: Modifier = Modifier, @StringRes textRes: Int) {
    Text(
        text = stringResource(id = textRes),
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center,
        modifier = modifier.padding(
            start = SPACING_QUADRUPLE,
            end = SPACING_QUADRUPLE,
        )
    )
}

@Composable
fun NavigationDots(
    modifier: Modifier = Modifier,
    dotsType: OnBoardingScrollingType,
    currentPage: Int = FIRST_PAGE_INDEX,
) {
    if (dotsType == OnBoardingScrollingType.BOTTOM_DOTS) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier.padding(bottom = SPACING_QUADRUPLE)
        ) {
            NavigationDotsSequence(currentPage)
        }
    } else {
        Column {
            NavigationDotsSequence(currentPage)
        }
    }

}

@Composable
fun NavigationDotsSequence(currentPage: Int) {
    NavigationDot(
        selected = currentPage == FIRST_PAGE_INDEX,
    )
    NavigationDot(
        selected = currentPage == SECOND_PAGE_INDEX,
    )
    NavigationDot(
        selected = currentPage == THIRD_PAGE_INDEX,
    )
}


@Composable
fun NavigationDot(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    val color = if (selected)
        MaterialTheme.colors.primary
    else
        MaterialTheme.colors.secondaryVariant

    Box(
        modifier = modifier
            .padding(SPACING_NORMAL)
            .size(DOT_SIZE)
            .clip(CircleShape)
            .background(color = color)
    )

}


@Composable
fun GetStartedButton(modifier: Modifier = Modifier, enabled: Boolean, onClick: () -> Unit) {
    NormalButton(
        modifier = modifier
            .padding(SPACING_DOUBLE)
            .fillMaxWidth()
            .clip(ButtonCornerShape),
        enabled = enabled,
        onClick = onClick
    ) { Text(text = stringResource(id = R.string.get_started)) }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SerialDecoderTheme {
        OnBoardingScreen(
            modifier = Modifier,
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
            {})
    }
}