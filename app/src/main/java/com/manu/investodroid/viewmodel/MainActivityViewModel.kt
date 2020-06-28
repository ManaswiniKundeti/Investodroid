package com.manu.investodroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.manu.investodroid.model.Stock
import com.manu.investodroid.repository.MainRepository
import com.manu.investodroid.viewstate.ViewState

class MainActivityViewModel(mainRepository: MainRepository) : ViewModel() {

    val stockLiveData : LiveData<ViewState<List<Stock>>> = mainRepository.stockListLiveData

    init {
        mainRepository.getStocksList()
    }
}