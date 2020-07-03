package com.manu.investodroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.manu.investodroid.model.FavouriteStock
import com.manu.investodroid.model.Stock
import com.manu.investodroid.model.StockDetail
import com.manu.investodroid.repository.StockDetailRepository
import com.manu.investodroid.viewstate.ViewState

class ProfileActivityViewModel(private val stockDetailRepository: StockDetailRepository) : ViewModel() {
    val detailLiveData : LiveData<ViewState<StockDetail>> = stockDetailRepository.stockDetailLiveData
    val stockLiveData : LiveData<ViewState<Stock>> = stockDetailRepository.stockLiveData

    fun fetchStockProfile(symbol :String){
        stockDetailRepository.getStockProfile(symbol)
    }

    fun insertStock(symbol : FavouriteStock){
        stockDetailRepository.insertFavouriteStock(symbol)
    }

    fun deleteStock(symbol : FavouriteStock){
        stockDetailRepository.deleteFavouriteStock(symbol)
    }
}