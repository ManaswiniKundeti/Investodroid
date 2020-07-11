package com.manu.investodroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.investodroid.model.Stock
import com.manu.investodroid.repository.StockListRepository
import com.manu.investodroid.viewstate.Error
import com.manu.investodroid.viewstate.Loading
import com.manu.investodroid.viewstate.Success
import com.manu.investodroid.viewstate.ViewState
import kotlinx.coroutines.launch

class MainActivityViewModel(private val stockListRepository: StockListRepository) : ViewModel() {

    private val _stockListLiveData: MutableLiveData<ViewState<List<Stock>?>> = MutableLiveData()
    val stockLiveData : LiveData<ViewState<List<Stock>?>> =_stockListLiveData

    private val stocks = mutableListOf<Stock>()

    fun getStocks(forceRefresh: Boolean = false) {
        if (!forceRefresh && (_stockListLiveData.value is Success || _stockListLiveData.value is Loading)) {
            return
        }

        viewModelScope.launch {
            _stockListLiveData.value = Loading
            val stockList = stockListRepository.getStocksList(forceRefresh)
            if (stockList == null || stockList.isEmpty()) {
                _stockListLiveData.value = Error("There was an error fetching stocks")
            } else {
                stocks.clear()
                stocks.addAll(stockList)

                _stockListLiveData.value = Success(stockList)
            }
        }
    }

    fun getFavStocks(){
        _stockListLiveData.value = Loading

        viewModelScope.launch {
            val favStocks = stockListRepository.getFavouriteStocks()
            _stockListLiveData.value = Success(favStocks)
        }
    }

    fun filterStockList(filter: String) {
        if (stocks.isNullOrEmpty()) {
            return;
        }

        val filterQuery = filter.trim()

        if (filterQuery.isEmpty()) {
            _stockListLiveData.value = Success(stocks)
            return
        }

        val filteredStocks = stocks.filter { it.name.contains(filterQuery, ignoreCase = true) }
        _stockListLiveData.value = Success(filteredStocks)
    }
}