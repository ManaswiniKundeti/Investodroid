package com.manu.investodroid.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manu.investodroid.R
import com.manu.investodroid.view.adapter.StocksListAdapter

class StocksListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //inflate layour for the fragment
        val view = inflater.inflate(R.layout.fragment_stocks_list, container, false)

        //display & update recycler view
        val stocksList: RecyclerView = view.findViewById(R.id.stocksList)
        stocksList.layoutManager = LinearLayoutManager(requireContext())

        val stocksListAdapter = StocksListAdapter(requireContext() as MainActivity)
        stocksList.adapter = stocksListAdapter

        //create observer to update Ui after network calls


        return view
    }

}