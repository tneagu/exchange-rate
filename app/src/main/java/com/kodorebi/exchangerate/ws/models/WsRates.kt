package com.kodorebi.exchangerate.ws.models

import java.util.*

/**
 * Created by TNE17909 on 4/8/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class WsRates {
    val rates: Map<String, Float> = mutableMapOf()
    val base: String = ""
    val date: Date = Date()
}