package com.serial.decoder.feature_decoding.data.local.util

import com.serial.decoder.R
import com.serial.decoder.core.util.UIText
import com.serial.decoder.feature_decoding.data.local.entity.ManufactureEntity


enum class Brands {
    SAMSUNG,
    LG,
}


// DECODING
object DecodingUtils : DecodingUtility {
    private val countryMap: HashMap<Char, Int> = HashMap()
    private val monthMap: HashMap<Char, String> = HashMap()
    private val yearMap: HashMap<Char, String> = HashMap()

    init {
        fillCountryMap()
        fillMonthMap()
        fillYearMap()
    }

    private fun fillCountryMap() {
        countryMap['1'] = R.string.korea
        countryMap['3'] = R.string.korea
        countryMap['4'] = R.string.romania
        countryMap['8'] = R.string.india
        countryMap['C'] = R.string.mexico
        countryMap['H'] = R.string.hungary
        countryMap['L'] = R.string.russia
        countryMap['M'] = R.string.malaysia
        countryMap['N'] = R.string.india
        countryMap['S'] = R.string.slovenia
        countryMap['W'] = R.string.china
    }

    private fun fillMonthMap() {
        monthMap['1'] = "01"
        monthMap['2'] = "02"
        monthMap['3'] = "03"
        monthMap['4'] = "04"
        monthMap['5'] = "05"
        monthMap['6'] = "06"
        monthMap['7'] = "07"
        monthMap['8'] = "08"
        monthMap['9'] = "09"
        monthMap['A'] = "10"
        monthMap['B'] = "11"
        monthMap['C'] = "12"
    }

    private fun fillYearMap() {
        yearMap['R'] = "2001"
        yearMap['T'] = "2002"
        yearMap['W'] = "2003"
        yearMap['X'] = "2004"
        yearMap['Y'] = "2005"
        yearMap['A'] = "2006|2021"
        yearMap['L'] = "2006"
        yearMap['P'] = "2007"
        yearMap['Q'] = "2008"
        yearMap['S'] = "2009"
        yearMap['Z'] = "2010"
        yearMap['B'] = "2011|2022"
        yearMap['C'] = "2012|2023"
        yearMap['D'] = "2013"
        yearMap['E'] = "2014"
        yearMap['G'] = "2015"
        yearMap['H'] = "2016"
        yearMap['J'] = "2017"
        yearMap['K'] = "2018"
        yearMap['M'] = "2019"
        yearMap['N'] = "2020"
    }


    override fun isCorrectSerial(serial: String, brand: Brands): Boolean {
        return when (brand) {
            Brands.SAMSUNG -> {
                serial.length == 15 || serial.length == 14
            }
            Brands.LG -> {
                false // TODO(Optional): You do this part (src: https://www.lg.com/ca_en/support/product-help/CT20098005-20150585049620)
            }


        }

    }

    //    decodeSerial("07953NEM600767H", Brands.SAMSUNG)
    override fun decodeSerial(serial: String, brand: Brands): ManufactureEntity {
        val type = getTypeFromSerial(serial, brand)
        val country = getCountryFromSerial(serial, brand)
        val date = getDateFromSerial(serial, brand)
        return ManufactureEntity(type = type, country = country, date = date)
    }

    private fun getTypeFromSerial(serial: String, brand: Brands): UIText {
        when (brand) {
            Brands.SAMSUNG -> {
                val tvProductCode = 3
                try {
                    val productCode: Char = serial[4]
                    val productCodeInNumber: Int = Character.getNumericValue(productCode)
                    if (productCodeInNumber == tvProductCode)
                        return UIText.StringResource(resId = R.string.tv)
                    return UIText.StringResource(resId = R.string.not_tv)
                } catch (numericException: NumberFormatException) {
                    return UIText.StringResource(
                        resId = R.string.couldnot_parse_numeric_value,
                        numericException.message ?: ""
                    )
                } catch (outOfBoundsException: IndexOutOfBoundsException) {
                    return UIText.StringResource(
                        resId = R.string.incorrect_serial_check_again,
                        outOfBoundsException.message ?: ""
                    )
                } catch (exception: Exception) {
                    return UIText.StringResource(
                        resId = R.string.couldnot_decode_serial_error_due_to,
                        exception.message ?: ""
                    )
                }
            }
            Brands.LG -> {
                return UIText.DynamicString("TODO: LG Type for now")  // TODO(Optional): You do this part (src: https://www.lg.com/ca_en/support/product-help/CT20098005-20150585049620)
            }
        }
    }

    private fun getCountryFromSerial(serial: String, brand: Brands): UIText {
        when (brand) {
            Brands.SAMSUNG -> {
                try {
                    val manufacturingCountry: Char = serial[5]
                    if (countryMap.containsKey(manufacturingCountry))
                        return UIText.StringResource(countryMap[manufacturingCountry]!!)

                    return UIText.StringResource(resId = R.string.couldnot_identify_country)
                } catch (numericException: NumberFormatException) {
                    return UIText.StringResource(
                        resId = R.string.parsing_number_error_due_to,
                        numericException.localizedMessage ?: ""
                    )
                } catch (exception: Exception) {
                    return UIText.StringResource(
                        resId = R.string.couldnot_decode_serial_error_due_to,
                        exception.localizedMessage ?: ""
                    )
                }
            }
            Brands.LG -> {
                return UIText.DynamicString("TODO LG for now") // TODO(Optional): You do this part (src: https://www.lg.com/ca_en/support/product-help/CT20098005-20150585049620)
            }
        }
    }

    private fun getDateFromSerial(serial: String, brand: Brands): UIText {
        when (brand) {
            Brands.SAMSUNG -> {
                val manufacturingYear: Char = serial[7]
                val manufacturingMonth: Char = serial[8]
                val isValidMonth = monthMap.containsKey(manufacturingMonth)
                val isValidYear = yearMap.containsKey(manufacturingYear)
                val isValidDate: Boolean = isValidYear && isValidMonth
                if (isValidDate)
                    return UIText.DynamicString("${yearMap[manufacturingYear]}/${monthMap[manufacturingMonth]}")
                else if (isValidYear)
                    return UIText.DynamicString("${yearMap[manufacturingYear]}")
                else if (isValidMonth)
                    return UIText.DynamicString("${monthMap[manufacturingMonth]}")

                return UIText.StringResource(resId = R.string.couldnot_identify_date)
            }
            Brands.LG -> {
                return UIText.DynamicString("TODO: LG Date for now") // TODO(Optional): Your part (src: https://www.lg.com/ca_en/support/product-help/CT20098005-20150585049620)
            }
        }
    }
}
