package com.kodorebi.exchangerate.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kodorebi.exchangerate.app.App
import com.kodorebi.exchangerate.models.Rate
import com.kodorebi.exchangerate.util.SharedPreferencesHelper
import com.kodorebi.exchangerate.ws.models.WsRates
import com.kodorebi.exchangerate.ws.services.RateWebService
import io.reactivex.Flowable.interval
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.kodein.di.generic.instance
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by TNE17909 on 4/10/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class RateListViewModel(application: Application) : AndroidViewModel(application) {
    private var prefHelper = SharedPreferencesHelper(getApplication())
    private val rateWebSerice: RateWebService by App.kodein.instance()
    private val disposable = CompositeDisposable()

    val rates = MutableLiveData<List<Rate>>()
    val timestamp = MutableLiveData<LocalDateTime>()
    val error = MutableLiveData<Boolean>()


    fun refresh() {
        val refreshTime = prefHelper.getRefreshTime();
        disposable.clear()
        disposable.add(
            Observable.interval(0, refreshTime, TimeUnit.SECONDS)
                .flatMap { rateWebSerice.getLatestRates("RON") }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<WsRates>() {
                    override fun onError(e: Throwable) {
                        error.value = true
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                    }

                    override fun onNext(response: WsRates) {
                        val result: MutableList<Rate> = mutableListOf()
                        response.rates?.let {
                            for (rate in it) {
                                val r = Rate(rate.key, rate.value)
                                result.add(r)
                            }

                        }
                        rates.value = result
                        error.value = false
                        timestamp.value = LocalDateTime.now();
                    }
                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}