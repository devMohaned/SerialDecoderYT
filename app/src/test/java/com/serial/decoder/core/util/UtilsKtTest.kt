package com.serial.decoder.core.util

import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import java.util.concurrent.TimeUnit

class UtilsKtTest {

    @Test
    fun shouldExpectDifferenceOf2Seconds_WhenLastFoundIsGreaterThanCurrentBy2000() {
        val currentTimestamp = 0L
        val lastFoundTimestamp = 2000L

        val expected = currentTimestamp - lastFoundTimestamp >= TimeUnit.SECONDS.toMillis(2)
        val actual  = canUpdate(currentTimestamp,lastFoundTimestamp,2L)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldExpectFalse_WhenLastFoundIsSmallerThanCurrent() {
        val currentTimestamp = 5000L
        val lastFoundTimestamp = 2000L

        val expected = canUpdate(currentTimestamp,lastFoundTimestamp,2000L)
        assertFalse(expected)
    }
}