package com.kodorebi.kotlinstarter.di.modules

import org.kodein.di.Kodein
import retrofit2.Retrofit
import kotlin.reflect.KClass

object WebServicesModuleBuilder {
    fun build(): Kodein.Module {
        return Kodein.Module("WebServicesModule") {
            fun <T : Any> create(retrofit: Retrofit, serviceClass: KClass<T>) : T {
                return retrofit.create(serviceClass.java)
            }

        }
    }
}