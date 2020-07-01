package com.manu.investodroid.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manu.investodroid.network.createInvestodroidService
import com.manu.investodroid.persistence.AppDatabase
import com.manu.investodroid.repository.StockListRepository
import java.lang.IllegalArgumentException

class MainActivityViewModelFactory(private val context : Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(StockListRepository(createInvestodroidService(), AppDatabase.getAppDatabase(context)!!.stockDao())) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}