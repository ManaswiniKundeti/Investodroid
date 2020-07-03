package com.manu.investodroid.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manu.investodroid.model.Stock
import com.manu.investodroid.network.IInvestodroidService
import com.manu.investodroid.persistence.FavouriteStockDao
import com.manu.investodroid.persistencex.StockDao
import com.manu.investodroid.viewstate.Error
import com.manu.investodroid.viewstate.Loading
import com.manu.investodroid.viewstate.Success
import com.manu.investodroid.viewstate.ViewState
import java.lang.Exception

class StockListRepository(private val investodroidService: IInvestodroidService,
                          private val stockDao: StockDao,
                          private val favouriteStockDao: FavouriteStockDao ) : IStockListRepository{

    private val TAG = StockListRepository::class.java.simpleName

    //LiveData
    private val _stocksListLiveData : MutableLiveData<ViewState<List<Stock>>> = MutableLiveData()
    val stockListLiveData : LiveData<ViewState<List<Stock>>> = _stocksListLiveData

    override fun getStocksList() {
        if (_stocksListLiveData.value is Success || _stocksListLiveData.value is Loading) {
            return
        }
        FetchStockListTask(_stocksListLiveData, investodroidService, stockDao).execute()
    }

    override fun getFavouriteStocks() {
        FetchFavStocksTask(_stocksListLiveData,favouriteStockDao).execute()
    }

    class FetchStockListTask(
        private val stockListLiveData : MutableLiveData<ViewState<List<Stock>>>,
        private val investodroidService: IInvestodroidService,
        private val stockDao: StockDao
    ) : AsyncTask<Void, Void, List<Stock>>(){

        private val TAG = FetchStockListTask::class.java.simpleName

        override fun onPreExecute() {
            super.onPreExecute()
            stockListLiveData.value = Loading
        }

        override fun doInBackground(vararg p0: Void): List<Stock>? {
            return try {
                // TODO Check if stocks are in database
                val response = investodroidService.fetchStockList().execute()
                if(response.isSuccessful && response.body() != null) {
                    val stockList = response.body()!!
                    stockDao.insertStocks(stockList)
                    stockList
                }else{
                    null
                }
            }catch (e : Exception){
                Log.e(TAG,e.message)
                null
            }
        }

        override fun onPostExecute(result: List<Stock>?) {
            super.onPostExecute(result)
            if (result == null) {
                stockListLiveData.value = Error("Error fetching list of stocks")
            } else {
                stockListLiveData.value = Success(result)
            }
        }

    }

    class FetchFavStocksTask(private val stockListLiveData : MutableLiveData<ViewState<List<Stock>>>,
                             private val favouriteStockDao: FavouriteStockDao) : AsyncTask<Void, Void, List<Stock>>(){

        private val TAG = FetchFavStocksTask::class.java.simpleName

        override fun onPreExecute() {
            super.onPreExecute()
            stockListLiveData.value = Loading
        }

        override fun doInBackground(vararg p0: Void?): List<Stock>? {
            return try {
                return favouriteStockDao.getFavouriteStocks()
            } catch (e: Exception) {
                Log.e(TAG, e.message)
                null
            }
        }

        override fun onPostExecute(result: List<Stock>?) {
            super.onPostExecute(result)
            if (result == null) {
                stockListLiveData.value = Error("Error fetching list of fav stocks")
            } else {
                stockListLiveData.value = Success(result)
            }
        }

    }
}