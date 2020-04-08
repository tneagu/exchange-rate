package com.kodorebi.exchangerate.di.modules

import android.content.Context
import com.kodorebi.exchangerate.db.domain.Db
import com.kodorebi.exchangerate.db.objectbox.ObjectBoxDb
import com.kodorebi.exchangerate.db.objectbox.models.MyObjectBox
import io.objectbox.BoxStore
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * Created by TNE17909 on 4/8/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
object ObjectBoxModule {
    fun build(context: Context) : Kodein.Module {
        return Kodein.Module("ObjectBoxModule") {
            bind<BoxStore>() with singleton {
                MyObjectBox.builder()
                    .androidContext(context)
                    .build()
            }

            bind<Db>() with singleton {
                ObjectBoxDb(instance())
            }


        }
    }
}