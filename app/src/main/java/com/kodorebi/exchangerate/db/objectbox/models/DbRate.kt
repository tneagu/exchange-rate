package com.kodorebi.exchangerate.db.objectbox.models

import com.kodorebi.exchangerate.db.domain.models.Rate
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

/**
 * Created by TNE17909 on 4/8/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */

@Entity
class DbRate(): Rate {
    @Id
    var localId: Long = 0
    override var currency: String = ""
    override var value: Double = 0.0
    override var date: Date = Date()
}