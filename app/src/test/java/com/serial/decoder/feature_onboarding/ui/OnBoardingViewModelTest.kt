package com.serial.decoder.feature_onboarding.ui

import com.serial.decoder.feature_onboarding.data.local.FakeSharedPreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

@ExperimentalCoroutinesApi
class OnBoardingViewModelTest {

    val viewModel: OnBoardingViewModel = OnBoardingViewModel(FakeSharedPreferenceManager())

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldUIStateLoading_WhenSetLoadingTrue() {
        viewModel.setLoading(true)
        assert(viewModel.uiState.value.isLoading == true)
    }

    @Test
    fun shouldUIStateNotLoading_WhenSetLoadingFalse() {
        viewModel.setLoading(false)
        assert(viewModel.uiState.value.isLoading == false)
    }


    @Test
    fun shouldCollectEventUpdateFirstLaunchEvent_WhenUpdateFirstLaunchedIsEmittedWithTrueValue() =
        runTest {
            viewModel.updateFirstLaunch(true)
            val event = viewModel.uiEvent.first()
            assert(event is OnBoardingUIEvent.UpdateFirstLaunch)
        }

    @Test
    fun shouldCollectEventUpdateFirstLaunchEvent_WhenUpdateFirstLaunchedIsEmittedWithFalseValue() =
        runTest {
            viewModel.updateFirstLaunch(false)
            val event = viewModel.uiEvent.first()
            assert(event is OnBoardingUIEvent.UpdateFirstLaunch)
        }


    @Test
    fun shouldCollectEventUpdateFirstLaunchEvent_WhenUpdateFirstLaunchedIsEmittedWithoutValue() =
        runTest {
            viewModel.updateFirstLaunch()
            val event = viewModel.uiEvent.first()
            assert(event is OnBoardingUIEvent.UpdateFirstLaunch)
        }


}