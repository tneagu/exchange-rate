package com.kodorebi.exchangerate.app

import androidx.multidex.MultiDexApplication
import org.kodein.di.Kodein
import com.kodorebi.exchangerate.di.KodeinBuilder

class App : MultiDexApplication() {
    companion object {
        lateinit var kodein : Kodein
            private set
    }


    override fun onCreate() {
        super.onCreate()
        kodein = KodeinBuilder.build(this)
    }
}