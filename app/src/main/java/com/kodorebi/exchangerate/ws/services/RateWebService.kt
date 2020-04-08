package com.kodorebi.exchangerate.ws.services

import com.kodorebi.exchangerate.db.domain.models.Rate
import retrofit2.http.GET

/**
 * Created by TNE17909 on 4/8/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
interface RateWebService {

    @GET("/latest")
    suspend fun getLatestRates() : Rate

}