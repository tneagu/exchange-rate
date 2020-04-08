package com.kodorebi.exchangerate.db.objectbox.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class DbRecord {
    @Id
    var id: Long = 0

    var key: String = ""
    var value: String = ""
}