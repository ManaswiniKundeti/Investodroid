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
                          private val sharedPreferenceHelper: SharedPreferenceHelper) : IStockListRepository{
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

        withContext(Dispatchers.IO) {
            _stocksListLiveData.postValue(Loading)

            val currentTimeStamp: Long = System.currentTimeMillis() / 1000

            val sharedPreferenceTSValue = sharedPreferenceHelper.getLong(NETWORK_TIMESTAMP_KEY, 0L)

            val hoursPassed = currentTimeStamp - sharedPreferenceTSValue

            try {
                if (hoursPassed >= TWENTY_FOUR_HOURS) {
                    sharedPreferenceHelper.putLong(NETWORK_TIMESTAMP_KEY, currentTimeStamp)
                    val response = investodroidService.fetchStockList()
                    if (response.isSuccessful && response.body() != null) {
                        val stockList = response.body()!!
                        stockDao.insertStocks(stockList)
                        _stocksListLiveData.postValue(Success(stockList))
                    } else {
                        _stocksListLiveData.postValue(Error("There was an error fetching stock list"))
                    }
                } else {
                    val stockList = stockDao.getStockList()
                    _stocksListLiveData.postValue(Success(stockList))//Fetch details from DB
                }
            } catch (e: Exception) {
                Log.e(TAG, "There was an error fetching stock list", e)
                _stocksListLiveData.postValue(Error("There was an error fetching stock list"))
            }
        }
    }

    override suspend fun getFavouriteStocks() {
        withContext(Dispatchers.IO){
            _stocksListLiveData.postValue(Loading)
            try {
                val favStocksList = favouriteStockDao.getFavouriteStocks()
                _stocksListLiveData.postValue(Success(favStocksList))
            } catch (e: Exception) {
                Log.e(TAG, "There was an error fetching favourite stocks", e)
                _stocksListLiveData.postValue(Error("There was an error fetching favourite stocks"))
            }
        }

    }
}