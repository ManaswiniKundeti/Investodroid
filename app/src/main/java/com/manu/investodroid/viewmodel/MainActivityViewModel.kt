package com.manu.investodroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.manu.investodroid.model.Stock
import com.manu.investodroid.repository.StockListRepository
import com.manu.investodroid.viewstate.ViewState

class MainActivityViewModel(stockListRepository: StockListRepository) : ViewModel() {

    val stockLiveData : LiveData<ViewState<List<Stock>>> = stockListRepository.stockListLiveData

    init {
        stockListRepository.getStocksList()
    }
}