package com.kodorebi.exchangerate.ws.services

import com.kodorebi.exchangerate.models.Rate
import com.kodorebi.exchangerate.ws.models.WsRates
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by TNE17909 on 4/8/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
interface RateWebService {

    @GET("/latest")
    fun getLatestRates() : Single<WsRates>

}