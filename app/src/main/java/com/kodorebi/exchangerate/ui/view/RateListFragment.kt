package com.kodorebi.exchangerate.ui.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kodorebi.exchangerate.R
import com.kodorebi.exchangerate.core.ui.FragmentBase
import com.kodorebi.exchangerate.ui.viewmodel.RateListViewModel
import kotlinx.android.synthetic.main.fragment_rate_list.*

/**
 * Created by TNE17909 on 4/9/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class RateListFragment : FragmentBase(R.layout.fragment_rate_list){

    private val ratesAdapter = RatesAdapter()
    val viewModel : RateListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refresh()

        ratesRecycler.apply {
            adapter = ratesAdapter
        }

        ovserveViewModel()
    }

    private fun ovserveViewModel() {
        viewModel.rates.observe(viewLifecycleOwner,  Observer { rates ->
            rates?.let{
                ratesAdapter.setItems(rates)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let{
                if(it){
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let{
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }
}