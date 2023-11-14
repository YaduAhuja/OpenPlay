package com.yaindustries.openplay.utils

object TimeUtils {
    fun convertIntTimeToHHMMSSTime(time: Int): String = "${time / 3600}:${time / 60}:${time % 60}"
}