package com.manu.investodroid.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manu.investodroid.model.FavouriteStock
import com.manu.investodroid.model.StockDetail
import com.manu.investodroid.network.IInvestodroidService
import com.manu.investodroid.persistence.FavouriteStockDao
import com.manu.investodroid.persistence.StockDetailDao
import com.manu.investodroid.viewstate.Error
import com.manu.investodroid.viewstate.Loading
import com.manu.investodroid.viewstate.Success
import com.manu.investodroid.viewstate.ViewState

class StockDetailRepository(
    private val investodroidService: IInvestodroidService,
    private val favouriteStockDao: FavouriteStockDao,
    private val stockDetailDao: StockDetailDao
) : IStockDetailRepository{

    private val TAG = StockDetailRepository::class.java.simpleName

    //LiveData
    private val _stockDetailLiveData : MutableLiveData<ViewState<StockDetail>> = MutableLiveData()
    val stockDetailLiveData : LiveData<ViewState<StockDetail>> = _stockDetailLiveData

    private val _isFavLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFavLiveData: LiveData<Boolean> = _isFavLiveData

    override suspend fun getStockDetails(symbol: String) {
        _stockDetailLiveData.value = Loading
        try{
            val response = investodroidService.fetchStockDetails(symbol)
            if(response.isSuccessful && response.body() != null){
                val stockDetails = response.body()
                if (stockDetails != null) {
                    stockDetailDao.insertStockDetails(stockDetails)
                    _stockDetailLiveData.value = Success(stockDetails)
                }else{
                    _stockDetailLiveData.value = Error("Error fetching stock details")
                }
            }else{
                _stockDetailLiveData.value = Error("Error fetching stock details")
            }
        }catch (e :Exception){
            Log.e(TAG,"Error fetching stock details",e)
            _stockDetailLiveData.value = Error("Error fetching stock details")
        }
    }

    override suspend fun insertFavouriteStock(symbol: String) {
        try{
                favouriteStockDao.insertFavouriteStock(FavouriteStock(symbol))
            } catch (e :Exception){
            Log.e(TAG,"Error inserting favourite stock", e)
        }
    }

    override suspend fun deleteFavouriteStock(symbol: String) {
        try{
                favouriteStockDao.deleteFavouriteStock(FavouriteStock(symbol))
        }catch (e :Exception){
            Log.e(TAG,"Error deleting favourite stock", e)
        }
    }

    override suspend fun isStockFavorite(symbol: String) {
        val favouriteStock = favouriteStockDao.getFavoriteStock(symbol)
        _isFavLiveData.value = favouriteStock.isNotEmpty()
    }

}