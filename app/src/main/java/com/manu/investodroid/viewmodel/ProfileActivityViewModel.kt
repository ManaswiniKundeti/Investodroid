package com.manu.investodroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.manu.investodroid.model.Stock
import com.manu.investodroid.model.StockProfile
import com.manu.investodroid.repository.StockProfileRepository
import com.manu.investodroid.viewstate.ViewState

class ProfileActivityViewModel(private val stockProfileRepository: StockProfileRepository) : ViewModel() {
    val profileLiveData : LiveData<ViewState<StockProfile>> = stockProfileRepository.stockProfileLiveData

    fun fetchStockProfile(name :String){
        stockProfileRepository.getStockProfile(name)
    }
}