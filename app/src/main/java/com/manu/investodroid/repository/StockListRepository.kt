package com.manu.investodroid.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manu.investodroid.helpers.SharedPreferenceHelper
import com.manu.investodroid.model.Stock
import com.manu.investodroid.network.IInvestodroidService
import com.manu.investodroid.persistence.FavouriteStockDao
import com.manu.investodroid.persistence.StockDao
import com.manu.investodroid.viewstate.Error
import com.manu.investodroid.viewstate.Loading
import com.manu.investodroid.viewstate.Success
import com.manu.investodroid.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StockListRepository(private val investodroidService: IInvestodroidService,
                          private val stockDao: StockDao,
                          private val favouriteStockDao: FavouriteStockDao,
                          private val sharedPreferenceHelper: SharedPreferenceHelper) : IStockListRepository {
    companion object {
        private const val NETWORK_TIMESTAMP_KEY = "network_call_time_stamp"
        private const val TWENTY_FOUR_HOURS = 24 * 60 * 60 * 1000
    }

    private val TAG = StockListRepository::class.java.simpleName

    //LiveData
    private val _stocksListLiveData : MutableLiveData<ViewState<List<Stock>>> = MutableLiveData()
    val stockListLiveData : LiveData<ViewState<List<Stock>>> = _stocksListLiveData

    override suspend fun getStocksList() {
        if (_stocksListLiveData.value is Success || _stocksListLiveData.value is Loading) {
            return
        }

        Log.d(TAG, "Running on ${Thread.currentThread().name}")
        //_stocksListLiveData.postValue(Loading)
        _stocksListLiveData.value = Loading

        val currentTimeStamp: Long = System.currentTimeMillis() / 1000

        val sharedPreferenceTSValue = sharedPreferenceHelper.getLong(NETWORK_TIMESTAMP_KEY, 0L)

        val hoursPassed = currentTimeStamp - sharedPreferenceTSValue

        try {
            if (hoursPassed >= TWENTY_FOUR_HOURS) {
                sharedPreferenceHelper.putLong(NETWORK_TIMESTAMP_KEY, currentTimeStamp)
                val response = investodroidService.fetchStockList()
                Log.d(TAG, "Running on ${Thread.currentThread().name}")
                if (response.isSuccessful && response.body() != null) {
                    val stockList = response.body()!!
                    stockDao.insertStocks(stockList)
                    //_stocksListLiveData.postValue(Success(stockList))
                    _stocksListLiveData.value = Success(stockList)

                } else {
                    _stocksListLiveData.value = Error("There was an error fetching stock list")
                }
            } else {
                val stockList = stockDao.getStockList()
                Log.d(TAG, "Running on ${Thread.currentThread().name}")
                _stocksListLiveData.value = Success(stockList)
            }
        } catch (e: Exception) {
            Log.e(TAG, "There was an error fetching stock list", e)
            _stocksListLiveData.value = Error("There was an error fetching stock list")
        }
    }

    override suspend fun getFavouriteStocks() {
        _stocksListLiveData.value = Loading
        try {
            val favStocksList = favouriteStockDao.getFavouriteStocks()
            _stocksListLiveData.value = Success(favStocksList)
        } catch (e: Exception) {
            Log.e(TAG, "There was an error fetching favourite stocks", e)
            _stocksListLiveData.value = Error("There was an error fetching stock list")
        }
    }
}