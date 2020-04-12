package com.kodorebi.exchangerate.ws.models

import java.util.*

/**
 * Created by TNE17909 on 4/12/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class WsHistory {

    val rates: Map<Date, Map<String, Float>> = mutableMapOf()
    val base: String = ""
}