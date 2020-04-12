package com.kodorebi.exchangerate.ws.services

import com.kodorebi.exchangerate.models.Rate
import com.kodorebi.exchangerate.ws.models.WsHistory
import com.kodorebi.exchangerate.ws.models.WsRates
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.DisposableObserver
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by TNE17909 on 4/8/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
interface RateWebService {

    @GET("/latest")
    fun getLatestRates(@Query("base") base: String): Observable<WsRates>

    @GET("/history")
    fun getHistory(
        @Query("start_at") startAt: String,
        @Query("end_at") endAt: String,
        @Query("base") base: String,
        @Query("symbols") currency: String
    ): Single<WsHistory>

}