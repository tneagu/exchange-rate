package com.kodorebi.kotlinstarter.di.modules

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.kodorebi.kotlinstarter.ws.gson.UtcDateTypeAdapter
import java.util.*

object RetrofitModuleBuilder {
    fun build(app: Application): Kodein.Module {
        return Kodein.Module("RetrofitModule") {
            bind<Cache>() with singleton {
                val cacheSize = 20L * 1024 * 1024
                Cache(app.cacheDir, cacheSize)
            }

            bind<Gson>() with singleton {
                val dateFormat : String = instance(tag="cfg.ws.dateFormat")
                val builder = GsonBuilder()
                builder.registerTypeAdapter(Date::class.java, UtcDateTypeAdapter())
                    .setDateFormat(dateFormat)
                    .create()
            }

            bind<HttpLoggingInterceptor>() with singleton {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                interceptor
            }



            bind<OkHttpClient>() with singleton {
                val loggingInterceptor : HttpLoggingInterceptor = instance()

                OkHttpClient.Builder()
                    .cache(instance())
                    .addInterceptor(loggingInterceptor)
                    .build()
            }

            bind<Retrofit>() with singleton {
                val baseUrl : String  = instance(tag = "cfg.ws.baseUrl")

                Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(instance()))
                    .baseUrl(baseUrl)
                    .client(instance())
                    .build()
            }
        }
    }
}