package com.kodorebi.exchangerate.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kodorebi.exchangerate.R
import com.kodorebi.exchangerate.databinding.ItemRateBinding
import com.kodorebi.exchangerate.models.Rate

/**
 * Created by TNE17909 on 4/9/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class RatesAdapter : RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

    var items: List<Rate> = listOf()
        private set


    fun setItems(items : List<Rate>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemRateBinding>(inflater, ViewHolder.layoutId, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.rate = items[position]
    }


    class ViewHolder(var view: ItemRateBinding) : RecyclerView.ViewHolder(view.root) {
        companion object{
            const val layoutId = R.layout.item_rate
        }
    }


}