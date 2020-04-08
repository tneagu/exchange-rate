package com.kodorebi.exchangerate.core.errormessages

import android.content.Context
import com.google.gson.Gson
import retrofit2.HttpException
import com.kodorebi.exchangerate.R
import com.kodorebi.exchangerate.ws.models.ApiErrors
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException

class ErrorMessages : IErrorMessages {

    companion object {
        private val gson = Gson()
    }

    //TODO: define lists for defaults by error class
    //TODO: add ability to override the message for a certain error code

    override fun getMessage(throwable: Throwable, context: Context): String {
        return when (throwable) {
            is HttpException -> getMessage(throwable, context)
            is SocketTimeoutException -> getMessage(throwable, context)
            is ConnectException -> getMessage(throwable, context)
            else -> throwable.localizedMessage
                ?: context.getString(R.string.error_unexpected)
        }
    }

    private fun getMessage(httpException: HttpException, context: Context) : String {
        val rawErrorBody = httpException.response()
            ?.errorBody()
            ?.string()
            ?: return context.getString(R.string.error_unexpected)

        val apiErrors : ApiErrors = try {
            gson.fromJson(rawErrorBody, ApiErrors::class.java)
        }
        catch(ex: Exception) {
            return context.getString(R.string.error_unexpected)
        }

        val firstApiError = apiErrors.errors.firstOrNull()
            ?: return context.getString(R.string.error_unexpected)

        //TODO: normally, we use the error code to determine a localized error message
        //  For now, we will use the error description from the server.

        return firstApiError.description
    }

    private fun getMessage(socketTimeoutException: SocketTimeoutException, context: Context) : String {
        println(socketTimeoutException.message)
        return context.getString(R.string.error_socket_timeout)
    }

    private fun getMessage(connectException: ConnectException, context: Context) : String {
        println(connectException.message)
        return context.getString(R.string.error_cannot_connect_to_server)
    }
}