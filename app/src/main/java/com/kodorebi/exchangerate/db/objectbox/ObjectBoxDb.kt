package com.kodorebi.exchangerate.db.objectbox

import com.google.gson.Gson
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import com.kodorebi.exchangerate.db.domain.Db
import com.kodorebi.exchangerate.db.objectbox.models.DbRecord
import com.kodorebi.exchangerate.db.objectbox.models.DbRecord_
import kotlin.reflect.KClass

class ObjectBoxDb(private val store: BoxStore) : Db {
    companion object {
        val gson = Gson()
    }

    private val recordsBox : Box<DbRecord> by lazy { store.boxFor(DbRecord::class) }

    override fun <T: Any> set(key: String, value: T?) {
        if (value != null) {
            val stringValue = gson.toJson(value)
            var dbRecord = findRecordByKey(key)

            if (dbRecord == null){
                dbRecord = DbRecord()
                dbRecord.key = key
            }

            dbRecord.value = stringValue
            recordsBox.put(dbRecord)
        }
        else {
            val dbRecord = findRecordByKey(key)
            if (dbRecord != null){
                recordsBox.remove(dbRecord)
            }
        }
    }

    override fun <T: Any> get(key: String, aClass: KClass<T>): T? {
        val dbRecord = findRecordByKey(key) ?: return null

        return try {
            val result: T = gson.fromJson(dbRecord.value, aClass.java)
            result
        } catch (e : Exception){
            null
        }
    }

    private fun findRecordByKey(key: String) : DbRecord? {
        return recordsBox.query()
            .equal(DbRecord_.key, key)
            .build()
            .findFirst()
    }

}