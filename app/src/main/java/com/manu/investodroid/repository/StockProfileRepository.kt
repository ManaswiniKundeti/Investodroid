package com.manu.investodroid.repository

import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manu.investodroid.helpers.SharedPreferenceHelper
import com.manu.investodroid.model.FavouriteStock
import com.manu.investodroid.model.Stock
import com.manu.investodroid.model.StockDetail
import com.manu.investodroid.network.IInvestodroidService
import com.manu.investodroid.persistence.FavouriteStockDao
import com.manu.investodroid.persistence.StockDetailDao
import com.manu.investodroid.persistencex.StockDao
import com.manu.investodroid.viewstate.Error
import com.manu.investodroid.viewstate.Loading
import com.manu.investodroid.viewstate.Success
import com.manu.investodroid.viewstate.ViewState
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime
import kotlin.math.abs

class StockProfileRepository(
    private val investodroidService: IInvestodroidService,
    private val stockDao: StockDao,
    private val favouriteStockDao: FavouriteStockDao,
    private val stockDetailDao: StockDetailDao,
    private val sharedPreferenceHelper: SharedPreferenceHelper
) : IStockProfileRepository{

    companion object {
        private const val NETWORK_TIMESTAMP_KEY = "network_call_time_stamp"
    }

    private val TAG = StockProfileRepository::class.java.simpleName

    //LiveData
    private val _stockProfileLiveData : MutableLiveData<ViewState<StockDetail>> = MutableLiveData()
    val stockProfileLiveData : LiveData<ViewState<StockDetail>> = _stockProfileLiveData

    private val _stockLiveData : MutableLiveData<ViewState<Stock>> = MutableLiveData()
    val stockLiveData : LiveData<ViewState<Stock>> = _stockLiveData

    override fun getStockProfile(symbol: String) {
        FetchStockProfileTask(_stockProfileLiveData,investodroidService,stockDetailDao, sharedPreferenceHelper).execute(symbol)
    }

    override fun insertFavouriteStock(symbol: FavouriteStock) {
        InsertFavouriteStockTask(favouriteStockDao).execute(symbol)
    }

    override fun deleteFavouriteStock(symbol: FavouriteStock) {
        DeleteFavouriteStockTask(favouriteStockDao).execute(symbol)
    }

    class FetchStockProfileTask( val _stockProfileLiveData : MutableLiveData<ViewState<StockDetail>>,
                                 private val investodroidService: IInvestodroidService,
                                 private val stockDetailDao: StockDetailDao,
                                 private val sharedPreferenceHelper: SharedPreferenceHelper) : AsyncTask<String, Void, StockDetail>(){
        private val TAG = FetchStockProfileTask::class.java.simpleName

        override fun onPreExecute() {
            super.onPreExecute()
            _stockProfileLiveData.value = Loading
        }

        override fun doInBackground(vararg p0: String?): StockDetail? {
            val currentTimeStamp : Long = System.currentTimeMillis() / 1000
            val twentyFourHours = 2 * 60 * 1000 //24 * 60 * 60 * 1000;

            val sharedPreferenceTSValue = sharedPreferenceHelper.getLong(NETWORK_TIMESTAMP_KEY, currentTimeStamp + twentyFourHours)

            val hoursPassed = currentTimeStamp - sharedPreferenceTSValue
            if(hoursPassed >= twentyFourHours){
                return try{
                    sharedPreferenceHelper.putLong(NETWORK_TIMESTAMP_KEY, currentTimeStamp)
                    val response = investodroidService.fetchStockDetails(p0[0].toString()).execute()
                    if(response.isSuccessful && response.body() != null){
                        val stockDetails = response.body()
                        if (stockDetails != null) {
                            stockDetailDao.insertStockDetails(stockDetails)
                            stockDetails
                        }else{
                            null
                        }
                    }else{
                        null
                    }
                }catch (e :Exception){
                    Log.e(TAG,e.message)
                    null
                }
            }else{
                //Fetch details from DB
                return try{
                    stockDetailDao.getStockDetails(p0[0].toString())
                }catch (e :Exception){
                    Log.e(TAG,e.message)
                    null
                }
            }
        }

        override fun onPostExecute(result: StockDetail?) {
            super.onPostExecute(result)
            if (result == null) {
                _stockProfileLiveData.value = Error("Error fetching stock details")
            } else {
                _stockProfileLiveData.value = Success(result)
            }
        }
    }

    class InsertFavouriteStockTask(private val favouriteStockDao: FavouriteStockDao ) : AsyncTask<FavouriteStock, Void, Void>(){

        private val TAG = InsertFavouriteStockTask::class.java.simpleName

        override fun doInBackground(vararg p0: FavouriteStock? ): Void? {
            try{
                val symbol = p0[0]
                if(symbol != null){
                    favouriteStockDao.insertFavouriteStock(symbol)
                }
            }catch (e :Exception){
                Log.e(TAG,e.message)
            }
            return null
        }
    }

    class DeleteFavouriteStockTask(private val favouriteStockDao: FavouriteStockDao ) : AsyncTask<FavouriteStock, Void, Void>(){

        private val TAG = InsertFavouriteStockTask::class.java.simpleName

        override fun doInBackground(vararg p0: FavouriteStock? ): Void? {
            try{
                val symbol = p0[0]
                if(symbol != null){
                    favouriteStockDao.deleteFavouriteStock(symbol)
                }
            }catch (e :Exception){
                Log.e(TAG,e.message)
            }
            return null
        }
    }

}