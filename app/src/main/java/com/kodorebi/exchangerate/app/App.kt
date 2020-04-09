package com.kodorebi.exchangerate.app

import android.app.Application
import org.kodein.di.Kodein
import com.kodorebi.exchangerate.di.KodeinBuilder

class App : Application() {
    companion object {
        lateinit var kodein : Kodein
            private set
    }


    override fun onCreate() {
        super.onCreate()
        kodein = KodeinBuilder.build(this)
    }
}