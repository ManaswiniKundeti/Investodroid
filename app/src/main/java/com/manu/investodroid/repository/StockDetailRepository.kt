package com.manu.investodroid.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manu.investodroid.helpers.SharedPreferenceHelper
import com.manu.investodroid.model.FavouriteStock
import com.manu.investodroid.model.StockDetail
import com.manu.investodroid.network.IInvestodroidService
import com.manu.investodroid.persistence.FavouriteStockDao
import com.manu.investodroid.persistence.StockDetailDao
import com.manu.investodroid.persistencex.StockDao
import com.manu.investodroid.viewstate.Error
import com.manu.investodroid.viewstate.Loading
import com.manu.investodroid.viewstate.Success
import com.manu.investodroid.viewstate.ViewState

class StockDetailRepository(
    private val investodroidService: IInvestodroidService,
    private val stockDao: StockDao,
    private val favouriteStockDao: FavouriteStockDao,
    private val stockDetailDao: StockDetailDao,
    private val sharedPreferenceHelper: SharedPreferenceHelper
) : IStockDetailRepository{

    private val TAG = StockDetailRepository::class.java.simpleName

    //LiveData
    private val _stockDetailLiveData : MutableLiveData<ViewState<StockDetail>> = MutableLiveData()
    val stockDetailLiveData : LiveData<ViewState<StockDetail>> = _stockDetailLiveData

    private val _isFavLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFavLiveData: LiveData<Boolean> = _isFavLiveData

    override fun getStockProfile(symbol: String) {
        FetchStockProfileTask(_stockDetailLiveData,investodroidService,stockDetailDao, sharedPreferenceHelper).execute(symbol)
    }

    override fun insertFavouriteStock(symbol: String) {
        InsertFavouriteStockTask(favouriteStockDao).execute(FavouriteStock(symbol))
    }

    override fun deleteFavouriteStock(symbol: String) {
        DeleteFavouriteStockTask(favouriteStockDao).execute(FavouriteStock(symbol))
    }

    override fun isStockFavorite(symbol: String) {
        IsFavoriteTask(favouriteStockDao, _isFavLiveData).execute(symbol)
    }

    class FetchStockProfileTask(private val _stockDetailLiveData : MutableLiveData<ViewState<StockDetail>>,
                                private val investodroidService: IInvestodroidService,
                                private val stockDetailDao: StockDetailDao,
                                private val sharedPreferenceHelper: SharedPreferenceHelper) : AsyncTask<String, Void, StockDetail>(){
        private val TAG = FetchStockProfileTask::class.java.simpleName

        override fun onPreExecute() {
            super.onPreExecute()
            _stockDetailLiveData.value = Loading
        }

        override fun doInBackground(vararg p0: String?): StockDetail? {
            return try{
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
        }

        override fun onPostExecute(result: StockDetail?) {
            super.onPostExecute(result)
            if (result == null) {
                _stockDetailLiveData.value = Error("Error fetching stock details")
            } else {
                _stockDetailLiveData.value = Success(result)
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

    class IsFavoriteTask(private val favouriteStockDao: FavouriteStockDao, private val _isFavLiveData: MutableLiveData<Boolean>) : AsyncTask<String, Void, Boolean>() {

        override fun doInBackground(vararg args: String): Boolean {
            val favouriteStock = favouriteStockDao.getFavoriteStock(args[0])
            return favouriteStock.isNotEmpty()
        }

        override fun onPostExecute(result: Boolean) {
            _isFavLiveData.value = result
        }

    }

}