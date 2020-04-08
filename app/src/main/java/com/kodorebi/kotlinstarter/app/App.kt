package com.kodorebi.kotlinstarter.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import com.kodorebi.kotlinstarter.di.KodeinBuilder

class App : Application() {
    companion object {
        lateinit var kodein : Kodein
            private set
    }


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        kodein = KodeinBuilder.build(this)
    }
}