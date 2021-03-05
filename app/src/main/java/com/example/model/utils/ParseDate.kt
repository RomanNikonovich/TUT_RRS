package com.example.model.utils

import java.text.SimpleDateFormat
import java.util.*

const val pattern = "EEE, dd MMM yyyy HH:mm:ss z"

fun parsePubDate(pubDate: String, locale: Locale = Locale.ENGLISH) =
        SimpleDateFormat(pattern, locale).parse(pubDate).time
