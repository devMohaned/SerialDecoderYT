package com.serial.decoder.feature_decoding.data.local.util

import com.serial.decoder.core.util.UIText
import com.serial.decoder.feature_decoding.data.local.entity.ManufactureEntity
import com.serial.decoder.feature_decoding.data.local.util.DecodingUtils.isCorrectSerial
import org.junit.Assert.*

import org.junit.Test

class DecodingUtilsTest {


    @Test
    fun shouldSamsungSerialBeCorrect_WhenCorrectSerialIsProvided() {
        val serial = "07953NEM600767H"
        val brand = Brands.SAMSUNG
        assertTrue(isCorrectSerial(serial, brand))
    }

    @Test
    fun shouldSamsungSerialBeInvalid_WhenInvalidSerialIsProvided() {
        val serial = "07953NEM6"
        val brand = Brands.SAMSUNG
        assertFalse(isCorrectSerial(serial, brand))
    }

    @Test
    fun shouldSamsungSerialBeInvalid_WhenInvalidBrandIsProvided() {
        val serial = "07953NEM600767"
        val brand = Brands.LG
        assertFalse(isCorrectSerial(serial, brand))
    }


    @Test
    fun shouldSamsungSerialBeInvalid_WhenEmptySerialIsProvided() {
        val serial = ""
        val brand = Brands.SAMSUNG
        assertFalse(isCorrectSerial(serial, brand))
    }


    @Test
    fun shouldDecodeCorrectSamsungSerial_WhenValidInputsAreProvided() {
        val serial = "07953NEM600767H"
        val brand = Brands.SAMSUNG

        val expectedDate = UIText.DynamicString("2019/06")

        val actual = DecodingUtils.decodeSerial(serial, brand)
        assertTrue(
            actual.date == expectedDate
        )

    }

    @Test
    fun shouldReturnFalseDecodeInvalidSamsungSerial_WhenValidInputsAreProvided() {
        val serial = "07953NEM0767H"
        val brand = Brands.SAMSUNG

        val expectedDate = UIText.DynamicString("2019/06")

        val actual = DecodingUtils.decodeSerial(serial, brand)
        assertFalse(
            actual.date == expectedDate
        )

    }
}