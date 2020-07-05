package com.manu.investodroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.investodroid.model.Stock
import com.manu.investodroid.repository.StockListRepository
import com.manu.investodroid.viewstate.ViewState
import kotlinx.coroutines.launch

class MainActivityViewModel(private val stockListRepository: StockListRepository) : ViewModel() {

    val stockLiveData : LiveData<ViewState<List<Stock>>> = stockListRepository.stockListLiveData

    fun getStocks() {
        viewModelScope.launch {
            stockListRepository.getStocksList()
        }
    }

    fun getFavStocks(){
        viewModelScope.launch {
            stockListRepository.getFavouriteStocks()
        }

    }
}