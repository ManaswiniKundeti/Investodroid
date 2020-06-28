package com.manu.investodroid.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manu.investodroid.model.Stock
import com.manu.investodroid.network.IInvestodroidService
import com.manu.investodroid.viewstate.Error
import com.manu.investodroid.viewstate.Loading
import com.manu.investodroid.viewstate.Success
import com.manu.investodroid.viewstate.ViewState
import java.lang.Exception

class MainRepository(private val investodroidService: IInvestodroidService) : IMainRepository{

    private val TAG = MainRepository::class.java.simpleName

    //LiveData
    private val _stocksListLiveData : MutableLiveData<ViewState<List<Stock>>> = MutableLiveData()
    val stockListLiveData : LiveData<ViewState<List<Stock>>> = _stocksListLiveData

    override fun getStocksList() {
        FetchStockListTask(_stocksListLiveData,investodroidService).execute()
    }

    class FetchStockListTask(
        private val stockListLiveData : MutableLiveData<ViewState<List<Stock>>>,
        private val investodroidService: IInvestodroidService
    ) : AsyncTask<Void, Void, List<Stock>>(){

        private val TAG = FetchStockListTask::class.java.simpleName

        override fun onPreExecute() {
            super.onPreExecute()
            stockListLiveData.value = Loading
        }

        override fun doInBackground(vararg p0: Void): List<Stock>? {
            return try {
                val response = investodroidService.fetchStockList().execute()
                if(response.isSuccessful && response.body() != null) {
                    response.body()
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
}