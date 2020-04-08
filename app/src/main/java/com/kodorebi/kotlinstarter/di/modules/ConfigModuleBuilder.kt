package com.kodorebi.kotlinstarter.di.modules

import org.kodein.di.Kodein
import org.kodein.di.generic.with

object ConfigModuleBuilder {
    fun build(): Kodein.Module {
        return Kodein.Module("ConfigModule") {
            constant(tag="cfg.ws.baseUrl") with "http://10.1.1.10:5000"
            constant(tag="cfg.ws.dateFormat") with "yyyy-MM-dd'T'HH:mm:ss"

            constant(tag="cfg.ui.dateFormat") with "dd-MM-yyyy"
        }
    }
}