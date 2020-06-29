package com.manu.investodroid.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manu.investodroid.model.StockProfile
import com.manu.investodroid.network.IInvestodroidService
import com.manu.investodroid.viewstate.Error
import com.manu.investodroid.viewstate.Loading
import com.manu.investodroid.viewstate.Success
import com.manu.investodroid.viewstate.ViewState
import java.lang.Exception

class StockProfileRepository(private val investodroidService: IInvestodroidService) : IStockProfileRepository{

    private val TAG = StockProfileRepository::class.java.simpleName

    //LiveData
    private val _stockProfileLiveData : MutableLiveData<ViewState<StockProfile>> = MutableLiveData()
    val stockProfileLiveData : LiveData<ViewState<StockProfile>> = _stockProfileLiveData

    override fun getStockProfile(symbol: String) {
        FetchStockProfileTask(_stockProfileLiveData,investodroidService).execute(symbol)
    }

    class FetchStockProfileTask( val _stockProfileLiveData : MutableLiveData<ViewState<StockProfile>>,
                                 private val investodroidService: IInvestodroidService
                                ) : AsyncTask<String, Void, StockProfile>(){

        private val TAG = FetchStockProfileTask::class.java.simpleName

        override fun onPreExecute() {
            super.onPreExecute()
            _stockProfileLiveData.value = Loading
        }

        override fun doInBackground(vararg p0: String?): StockProfile? {
            return try{
                val response = investodroidService.fetchStockDetails(p0[0].toString()).execute()
                if(response.isSuccessful && response.body() != null){
                    response.body()!!.profile
                }else{
                    null
                }
            }catch (e :Exception){
                Log.e(TAG,e.message)
                null
            }
        }

        override fun onPostExecute(result: StockProfile?) {
            super.onPostExecute(result)
            if (result == null) {
                _stockProfileLiveData.value = Error("Error fetching stock details")
            } else {
                _stockProfileLiveData.value = Success(result)
            }
        }

    }
}