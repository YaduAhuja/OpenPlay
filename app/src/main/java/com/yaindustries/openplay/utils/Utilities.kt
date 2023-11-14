package com.yaindustries.openplay.utils

import androidx.compose.runtime.MutableState

object Utilities {
    fun <T> updateValueByCheckingEquality(currentValue: MutableState<T>, newValue: T) {
        if (currentValue.value != newValue)
            currentValue.value = newValue
    }
}