package com.kodorebi.exchangerate.ui.view

import android.os.Bundle
import android.view.*
import com.kodorebi.exchangerate.R
import com.kodorebi.exchangerate.core.ui.FragmentBase
import kotlinx.android.synthetic.main.fragment_rate_list.*

/**
 * Created by TNE17909 on 4/9/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class RateListFragment : FragmentBase(R.layout.fragment_rate_list){

    private val ratesAdapter = RatesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ratesRecycler.apply {
            adapter = ratesAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }
}