package com.kodorebi.exchangerate.di.modules

import org.kodein.di.Kodein
import org.kodein.di.generic.with

object ConfigModuleBuilder {
    fun build(): Kodein.Module {
        return Kodein.Module("ConfigModule") {
            constant(tag="cfg.ws.baseUrl") with "https://api.exchangeratesapi.io/"
            constant(tag="cfg.ws.dateFormat") with "yyyy-MM-dd'T'HH:mm:ss"

            constant(tag="cfg.ui.dateFormat") with "dd-MM-yyyy"
        }
    }
}