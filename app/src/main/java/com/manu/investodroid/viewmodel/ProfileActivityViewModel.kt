package com.manu.investodroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.manu.investodroid.model.FavouriteStock
import com.manu.investodroid.model.Stock
import com.manu.investodroid.model.StockProfile
import com.manu.investodroid.repository.StockProfileRepository
import com.manu.investodroid.viewstate.ViewState

class ProfileActivityViewModel(private val stockProfileRepository: StockProfileRepository) : ViewModel() {
    val profileLiveData : LiveData<ViewState<StockProfile>> = stockProfileRepository.stockProfileLiveData
    val stockLiveData : LiveData<ViewState<Stock>> = stockProfileRepository.stockLiveData

    fun fetchStockProfile(name :String){
        stockProfileRepository.getStockProfile(name)
    }

    fun insertStock(symbol : FavouriteStock){
        stockProfileRepository.insertFavouriteStock(symbol)
    }

    fun deleteStock(symbol : FavouriteStock){
        stockProfileRepository.deleteFavouriteStock(symbol)
    }
}