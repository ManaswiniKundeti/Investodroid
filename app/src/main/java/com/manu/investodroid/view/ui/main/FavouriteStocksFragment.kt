package com.manu.investodroid.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manu.investodroid.R
import com.manu.investodroid.extensions.hide
import com.manu.investodroid.extensions.show
import com.manu.investodroid.model.Stock
import com.manu.investodroid.view.adapter.StocksListAdapter
import com.manu.investodroid.viewmodel.MainActivityViewModel
import com.manu.investodroid.viewmodel.MainActivityViewModelFactory
import com.manu.investodroid.viewstate.Error
import com.manu.investodroid.viewstate.Loading
import com.manu.investodroid.viewstate.Success
import com.manu.investodroid.viewstate.ViewState
import kotlinx.android.synthetic.main.fragment_stocks_list.*

class FavouriteStocksFragment : Fragment()  {
    private val viewmodelFactory by lazy { MainActivityViewModelFactory(requireContext()) }
    private val viewModel: MainActivityViewModel by viewModels {
        viewmodelFactory
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite_stocks, container, false)

        val favouriteStocksList : RecyclerView = view.findViewById(R.id.favouriteStocksList)
        favouriteStocksList.layoutManager = LinearLayoutManager(requireContext())

        val favouriteStocksListAdapter = StocksListAdapter(requireContext() as MainActivity)
        favouriteStocksList.adapter = favouriteStocksListAdapter

        //create observer to update Ui after network calls
        val favstocksListObserver = Observer<ViewState<List<Stock>>>{ viewState ->
            when(viewState) {
                is Success -> {
                    stocks_list_progress_bar.hide()
                    favouriteStocksListAdapter.setStocksList(viewState.data)
                }
                is Error -> {
                    stocks_list_progress_bar.hide()
                    Toast.makeText(requireContext(), viewState.errMsg, Toast.LENGTH_SHORT).show()
                }
                is Loading -> {
                    stocks_list_progress_bar.show()
                }
            }
        }
        viewModel.stockLiveData.observe(viewLifecycleOwner, favstocksListObserver)
        viewModel.getFavStocks()
        return view
    }
}