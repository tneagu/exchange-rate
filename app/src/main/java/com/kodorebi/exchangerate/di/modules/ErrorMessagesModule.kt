package com.kodorebi.exchangerate.di.modules

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import com.kodorebi.exchangerate.core.errormessages.IErrorMessages
import com.kodorebi.exchangerate.core.errormessages.ErrorMessages

object ErrorMessagesModule {
    fun build(): Kodein.Module {
        return Kodein.Module("ErrorMessagesModule") {
            bind<IErrorMessages>() with provider {
                ErrorMessages()
            }
        }
    }
}