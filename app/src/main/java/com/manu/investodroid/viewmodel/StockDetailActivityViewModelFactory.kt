package com.manu.investodroid.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manu.investodroid.helpers.SharedPreferenceHelper
import com.manu.investodroid.network.createInvestodroidService
import com.manu.investodroid.persistence.AppDatabase
import com.manu.investodroid.repository.StockDetailRepository
import java.lang.IllegalArgumentException

class StockDetailActivityViewModelFactory (private val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StockDetailActivityViewModel::class.java)){
            return StockDetailActivityViewModel(StockDetailRepository(createInvestodroidService(),
                AppDatabase.getAppDatabase(context).stockDao(),
                AppDatabase.getAppDatabase(context).favStockDao(),
                AppDatabase.getAppDatabase(context).stockDetailDao(),
                SharedPreferenceHelper(context)
            )) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}