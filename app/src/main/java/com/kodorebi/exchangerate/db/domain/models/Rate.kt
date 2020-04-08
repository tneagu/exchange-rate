package com.kodorebi.exchangerate.db.domain.models

import java.util.*

/**
 * Created by TNE17909 on 4/8/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
interface Rate {
    var currency: String
    var value: Double
    var date: Date
}