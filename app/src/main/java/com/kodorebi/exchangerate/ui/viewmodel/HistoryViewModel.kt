package com.kodorebi.exchangerate.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kodorebi.exchangerate.app.App
import com.kodorebi.exchangerate.models.Rate
import com.kodorebi.exchangerate.util.SharedPreferencesHelper
import com.kodorebi.exchangerate.ws.models.WsHistory
import com.kodorebi.exchangerate.ws.services.RateWebService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.kodein.di.generic.instance
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Created by TNE17909 on 4/12/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    companion object{
        const val DAYS_BEHIND : Long = 10
        const val CURRENCIES = "RON,USD,BGN"
    }

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private val rateWebSerice: RateWebService by App.kodein.instance()
    private val disposable = CompositeDisposable()

    val ronHistory = MutableLiveData<List<Rate>>()
    val usdHistory = MutableLiveData<List<Rate>>()
    val bgnHistory = MutableLiveData<List<Rate>>()


    fun getHistory(){
        val savedCurrency = prefHelper.getSavedCurrency()
        val currentDate = LocalDate.now()
        val startDate = currentDate.minusDays(DAYS_BEHIND)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        disposable.add(
            rateWebSerice.getHistory(formatter.format(startDate), formatter.format(currentDate), savedCurrency, CURRENCIES)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<WsHistory>(){
                    val result: MutableList<Rate> = mutableListOf()
                    override fun onSuccess(response: WsHistory) {
                        response.rates.let{
                            for(dateMap in it){
                                val date = dateMap.key
                                for(rate in dateMap.value){
                                    val r = Rate(rate.key, rate.value, date)
                                    result.add(r)
                                }
                            }
                        }

                        usdHistory.value = result.filter { it.currency == "USD" }.sortedBy { it.date }
                        ronHistory.value = result.filter { it.currency == "RON" }.sortedBy { it.date }
                        bgnHistory.value = result.filter { it.currency == "BGN" }.sortedBy { it.date }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }
}