package com.kodorebi.kotlinstarter.core.extensions

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.util.*

val Date.asLocalDate : LocalDate
    get() = Instant.ofEpochMilli(time)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()