package com.kodorebi.exchangerate.di.modules

import com.kodorebi.exchangerate.ws.services.RateWebService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import retrofit2.Retrofit
import kotlin.reflect.KClass

object WebServicesModuleBuilder {
    fun build(): Kodein.Module {
        return Kodein.Module("WebServicesModule") {
            fun <T : Any> create(retrofit: Retrofit, serviceClass: KClass<T>) : T {
                return retrofit.create(serviceClass.java)
            }

            bind<RateWebService>() with provider {
                create(instance(), RateWebService::class)
            }
        }
    }
}