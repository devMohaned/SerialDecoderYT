package com.serial.decoder.feature_faq.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import com.serial.decoder.R
import com.serial.decoder.core.ui.NormalButton
import com.serial.decoder.core.util.SPACING_DOUBLE
import com.serial.decoder.core.util.SPACING_NORMAL
import com.serial.decoder.core.util.SPACING_QUADRUPLE
import com.serial.decoder.core.util.SPACING_TRIPLE
import com.serial.decoder.feature_faq.data.RATE_THE_APP_URL
import com.serial.decoder.feature_faq.data.YOUTUBE_CHANNEL_URL
import com.serial.decoder.ui.theme.ButtonCornerShape


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FrequentlyAskedQuestionsScreen(modifier: Modifier = Modifier, onBackButtonPressed: () -> Unit) {
    val uriHandler = LocalUriHandler.current
    Scaffold(topBar = { FAQTopBar(modifier, onBackButtonPressed) }, bottomBar = {
        Column {
            ContactDeveloperButton() {
                uriHandler.openUri(YOUTUBE_CHANNEL_URL)
            }
            RateTheAppButton() {
                uriHandler.openUri(RATE_THE_APP_URL)
            }
        }
    })
    {
        FAQContent(modifier)
    }
}

@Composable
fun FAQContent(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(top = SPACING_TRIPLE)) {
        item() {
            Question(
                modifier = modifier, questionResId = R.string.faq_1, answerResId = R.string.ans_1
            )
        }
        item() {
            Question(
                modifier = modifier, questionResId = R.string.faq_2, answerResId = R.string.ans_2
            )
        }
        item() {
            Question(
                modifier = modifier, questionResId = R.string.faq_3, answerResId = R.string.ans_3
            )
        }
    }
}

@Composable
fun FAQTopBar(modifier: Modifier = Modifier, OnBackButtonPressed: () -> Unit) {
    TopAppBar(modifier = modifier, backgroundColor = MaterialTheme.colors.surface, title = {
        AppBarTitle()
    }, navigationIcon = {
        AppBarIcon(OnBackButtonPressed = OnBackButtonPressed)

    })
}

@Composable
fun AppBarIcon(modifier: Modifier = Modifier, OnBackButtonPressed: () -> Unit) {
    IconButton(modifier = modifier, onClick = OnBackButtonPressed) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(
                id = R.string.back_button
            ),
        )
    }
}

@Composable
fun AppBarTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.frequently_asked_questions),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h3,

        )
}

@Composable
fun Question(
    modifier: Modifier = Modifier, @StringRes questionResId: Int, @StringRes answerResId: Int
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.padding(vertical = SPACING_NORMAL),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(horizontal = SPACING_QUADRUPLE)
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.primaryVariant)
                .clickable {
                    isExpanded.value = !isExpanded.value
                }) {
            Icon(
                modifier = modifier.padding(horizontal = SPACING_NORMAL),
                painter = painterResource(id = if (!isExpanded.value) R.drawable.ic_dropdown else R.drawable.ic_collapse),
                contentDescription = stringResource(
                    id = R.string.dropdown_icon
                ),
                tint = Color.Unspecified
            )
            Text(
                modifier = modifier.padding(horizontal = SPACING_NORMAL),
                text = stringResource(id = questionResId),
                style = MaterialTheme.typography.body1,
            )

        }
        AnimatedVisibility(
            visible = isExpanded.value,
            enter = expandVertically(
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                ),
            ),
            exit = shrinkVertically(),
        ) {
            // Content that needs to appear/disappear goes here:
            Text(
                modifier = modifier.padding(
                    horizontal = SPACING_QUADRUPLE + SPACING_DOUBLE, vertical = SPACING_NORMAL
                ),
                text = stringResource(id = answerResId),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}


@Composable
fun ContactDeveloperButton(modifier: Modifier = Modifier, onButtonClicked: () -> Unit) {
    NormalButton(
        modifier = modifier
            .padding(
                horizontal = SPACING_QUADRUPLE, vertical = SPACING_NORMAL
            )
            .fillMaxWidth()
            .clip(ButtonCornerShape), onClick = onButtonClicked
    ) {
        Text(text = stringResource(id = R.string.contact_developer))
    }
}

@Composable
fun RateTheAppButton(modifier: Modifier = Modifier, onButtonClicked: () -> Unit) {
    NormalButton(
        modifier = modifier
            .padding(
                horizontal = SPACING_QUADRUPLE, vertical = SPACING_NORMAL
            )
            .fillMaxWidth()
            .clip(ButtonCornerShape), onClick = onButtonClicked
    ) {
        Text(text = stringResource(id = R.string.rate_the_app))
    }

}