package com.serial.decoder.core.util

import com.serial.decoder.R
import com.serial.decoder.feature_decoding.data.local.entity.ManufactureEntity
import java.util.concurrent.TimeUnit


fun canUpdate(
    currentTimestamp: Long,
    lastFoundTimestamp: Long,
    differenceTimeInSeconds: Long
): Boolean {
    return currentTimestamp - lastFoundTimestamp >= TimeUnit.SECONDS.toMillis(
        differenceTimeInSeconds
    )
}