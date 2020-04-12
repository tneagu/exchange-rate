package com.kodorebi.exchangerate.ui.view

import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.kodorebi.exchangerate.R
import com.kodorebi.exchangerate.core.ui.FragmentBase
import com.kodorebi.exchangerate.models.Rate
import com.kodorebi.exchangerate.ui.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.fragment_history.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by TNE17909 on 4/12/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class HistoryFragment : FragmentBase(R.layout.fragment_history) {
    val viewModel: HistoryViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.getHistory()
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.ronHistory.observe(viewLifecycleOwner, Observer { rates ->
            rates?.let {
                showHistory(ronChart, it, "RON")
            }

        })

        viewModel.usdHistory.observe(viewLifecycleOwner, Observer { rates ->
            rates?.let {
                showHistory(usdChart, it, "USD")
            }

        })

        viewModel.bgnHistory.observe(viewLifecycleOwner, Observer { rates ->
            rates?.let {
                showHistory(bgnChart, it, "BGN")
            }

        })
    }

    private fun showHistory(
        chartView: LineChart,
        rates: List<Rate>,
        label: String
    ) {
        val entries = rates.mapIndexed { index, rate ->
                Entry(index.toFloat(), rate.value)
            }

        val dataSet = LineDataSet(entries, label)
        dataSet.color = Color.RED
        dataSet.setDrawValues(true)
        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val rate = rates[value.toInt()]
                return String.format("%.2f", rate.value)
            }

        }

        val formatter = SimpleDateFormat("MM-dd", Locale.getDefault())
        val xAxis: XAxis = chartView.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val rate = rates[value.toInt()]
                return formatter.format(rate.date)
            }

            fun getDecimalDigits(): Int {
                return 0
            }
        }

        chartView.data = LineData(dataSet)
        chartView.axisLeft.setDrawLabels(false)
        chartView.axisRight.setDrawLabels(false)
        chartView.description.isEnabled = false
        chartView.invalidate()
    }
}