package com.kodorebi.exchangerate.db.domain.extensions

import com.kodorebi.exchangerate.db.domain.Db
import com.kodorebi.exchangerate.db.domain.enums.DbKey
import kotlin.reflect.KClass

fun <T : Any> Db.get(key: DbKey, aClass: KClass<T>) : T? {
    return get(key.toString(), aClass)
}

fun <T : Any> Db.set(key: DbKey, value: T?) {
    set(key.toString(), value)
}