package com.kodorebi.exchangerate.models

import java.util.*

/**
 * Created by TNE17909 on 4/10/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
data class Rate (
    val currency: String = "",
    val value: Float = 0.0f,
    val date : Date = Date()
)