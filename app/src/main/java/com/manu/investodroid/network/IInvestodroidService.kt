package com.manu.investodroid.network

import com.manu.investodroid.BuildConfig
import com.manu.investodroid.model.Stock
import com.manu.investodroid.model.StockDetail
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

fun createInvestodroidService() : IInvestodroidService {

    val okHttpClient = OkHttpClient
        .Builder()
        .build()

    val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://financialmodelingprep.com/api/v3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    return retrofit.create(IInvestodroidService::class.java)
}
interface IInvestodroidService {

    @GET("stock/list?apikey=${BuildConfig.FINANCIAL_MODELING_PREP_API_KEY}")
    suspend fun fetchStockList() : Response<List<Stock>>

    @GET("company/profile/{symbol}?apikey=${BuildConfig.FINANCIAL_MODELING_PREP_API_KEY}")
    fun fetchStockDetails(
        @Path("symbol") symbol :String
    ) : Call<StockDetail>

}