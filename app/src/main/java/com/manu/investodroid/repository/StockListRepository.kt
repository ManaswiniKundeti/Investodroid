package com.manu.investodroid.repository

import android.util.Log
import com.manu.investodroid.helpers.SharedPreferenceHelper
import com.manu.investodroid.model.Stock
import com.manu.investodroid.network.IInvestodroidService
import com.manu.investodroid.persistence.FavouriteStockDao
import com.manu.investodroid.persistence.StockDao

class StockListRepository(private val investodroidService: IInvestodroidService,
                          private val stockDao: StockDao,
                          private val favouriteStockDao: FavouriteStockDao,
                          private val sharedPreferenceHelper: SharedPreferenceHelper) : IStockListRepository {
    companion object {
        private const val NETWORK_TIMESTAMP_KEY = "network_call_time_stamp"
        private const val TWENTY_FOUR_HOURS = 24 * 60 * 60 * 1000
    }

    private val TAG = StockListRepository::class.java.simpleName

    override suspend fun getStocksList(forceApi: Boolean): List<Stock>? {
        val currentTimeStamp: Long = System.currentTimeMillis() / 1000
        val sharedPreferenceTSValue = sharedPreferenceHelper.getLong(NETWORK_TIMESTAMP_KEY, 0L)
        val hoursPassed = currentTimeStamp - sharedPreferenceTSValue

        val shouldUseApi = forceApi || hoursPassed >= TWENTY_FOUR_HOURS

        return try {
            if (shouldUseApi) {
                sharedPreferenceHelper.putLong(NETWORK_TIMESTAMP_KEY, currentTimeStamp)
                val response = investodroidService.fetchStockList()
                if (response.isSuccessful && response.body() != null) {
                    val stockList = response.body()!!
                    stockDao.insertStocks(stockList)
                    stockList
                } else {
                    null
                }
            } else {
                val stockList = stockDao.getStockList()
                Log.d(TAG, "Running on ${Thread.currentThread().name}")
                stockList
            }
        } catch (e: Exception) {
            Log.e(TAG, "There was an error fetching stock list", e)
            null
        }
    }

    override suspend fun getFavouriteStocks(): List<Stock>? {
        return try {
            return favouriteStockDao.getFavouriteStocks()
        } catch (e: Exception) {
            Log.e(TAG, "There was an error fetching favourite stocks", e)
            null
        }
    }
}