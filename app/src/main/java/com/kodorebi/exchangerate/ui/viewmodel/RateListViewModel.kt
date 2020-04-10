package com.kodorebi.exchangerate.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kodorebi.exchangerate.app.App
import com.kodorebi.exchangerate.models.Rate
import com.kodorebi.exchangerate.ws.models.WsRates
import com.kodorebi.exchangerate.ws.services.RateWebService
import io.objectbox.android.AndroidScheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.kodein.di.generic.instance

/**
 * Created by TNE17909 on 4/10/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class RateListViewModel(application: Application): AndroidViewModel(application) {

    private val rateWebSerice : RateWebService by App.kodein.instance()
    private val disposable = CompositeDisposable()

    val rates = MutableLiveData<List<Rate>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun refresh(){
        loading.value = true
        disposable.add(
            rateWebSerice.getLatestRates()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<WsRates>(){
                    override fun onSuccess(response: WsRates) {
                        val result : MutableList<Rate> = mutableListOf()
                        response.rates?.let {
                            for(rate in it){
                                val r = Rate(rate.key, rate.value)
                                result.add(r)
                            }

                        }
                        rates.value = result
                        loading.value = false
                        error.value = false
                    }

                    override fun onError(e: Throwable) {
                        error.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}