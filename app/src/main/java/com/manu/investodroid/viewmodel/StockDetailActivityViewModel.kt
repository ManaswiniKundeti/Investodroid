package com.manu.investodroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.investodroid.model.StockDetail
import com.manu.investodroid.repository.StockDetailRepository
import com.manu.investodroid.viewstate.ViewState
import kotlinx.coroutines.launch

class StockDetailActivityViewModel(private val stockDetailRepository: StockDetailRepository) : ViewModel() {
    val detailLiveData : LiveData<ViewState<StockDetail>> = stockDetailRepository.stockDetailLiveData
    val isFavLiveData = stockDetailRepository.isFavLiveData

    fun fetchStockProfile(symbol :String){
        viewModelScope.launch {
            stockDetailRepository.getStockDetails(symbol)
        }
    }

    fun insertStock(symbol : String){
        viewModelScope.launch {
            stockDetailRepository.insertFavouriteStock(symbol)
        }

    }

    fun deleteStock(symbol : String){
        viewModelScope.launch {
            stockDetailRepository.deleteFavouriteStock(symbol)
        }
    }

    fun isStockFavorite(symbol: String) {
        viewModelScope.launch {
            stockDetailRepository.isStockFavorite(symbol)
        }
    }
}