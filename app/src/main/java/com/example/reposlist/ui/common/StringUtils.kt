package com.example.reposlist.common

import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.getFormattedDate(): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTime = LocalDateTime.parse(this, formatter)

        // Get the user's default time zone
        val defaultZoneId = ZoneId.systemDefault()
        val zonedDateTime = localDateTime.atZone(defaultZoneId)

        // Format the date to the desired pattern
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        zonedDateTime.format(outputFormatter)
    } catch (e: Exception) {
        ""
    }
}

fun String.formatSize(): String {
    val bytes = this.toLongOrNull() ?: return ""

    val kb = 1024L
    val mb = kb * kb

    val formatter = NumberFormat.getInstance(Locale.getDefault())
    formatter.maximumFractionDigits = 2

    return when {
        bytes < kb -> "$bytes B"
        bytes < mb -> "${formatter.format(bytes / kb)} KB"
        else -> "${formatter.format(bytes / mb)} MB"
    }
}