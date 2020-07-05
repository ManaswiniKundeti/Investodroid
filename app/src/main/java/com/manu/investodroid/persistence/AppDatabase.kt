package com.manu.investodroid.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manu.investodroid.model.FavouriteStock
import com.manu.investodroid.model.Stock
import com.manu.investodroid.model.StockDetail
import com.manu.investodroid.persistencex.StockDao

@Database(entities = [Stock::class, FavouriteStock::class, StockDetail::class], version = 1)
@TypeConverters(value = [StockProfileConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao() : StockDao
    abstract fun favStockDao() : FavouriteStockDao
    abstract fun stockDetailDao() : StockDetailDao

    companion object {
        var INSTANCE : AppDatabase? = null

        fun getAppDatabase(context : Context) : AppDatabase {
            if(INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"myDB")
                        .build()
                }
            }
            return INSTANCE!!
        }

        fun destroyDatabase(){
            INSTANCE = null
        }
    }
}