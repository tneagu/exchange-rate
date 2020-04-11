package com.kodorebi.exchangerate.ui.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kodorebi.exchangerate.R
import com.kodorebi.exchangerate.core.ui.FragmentBase
import com.kodorebi.exchangerate.ui.viewmodel.RateListViewModel
import kotlinx.android.synthetic.main.fragment_rate_list.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Created by TNE17909 on 4/9/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class RateListFragment : FragmentBase(R.layout.fragment_rate_list) {

    private val ratesAdapter = RatesAdapter()
    val viewModel: RateListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ratesRecycler.apply {
            adapter = ratesAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.rates.observe(viewLifecycleOwner, Observer { rates ->
            rates?.let {
                ratesAdapter.setItems(rates)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                if (it) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        })


        viewModel.timestamp.observe(viewLifecycleOwner, Observer { timestamp ->
            timestamp?.let {
                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                lastUpdatedTextView.text = String.format(getString(R.string.last_updated), it.format(formatter))
            }
        })
    }

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.actionSettings ->{
                view?.let{
                    navController.navigate(RateListFragmentDirections.toSettingsFragment())
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    //endregion

}