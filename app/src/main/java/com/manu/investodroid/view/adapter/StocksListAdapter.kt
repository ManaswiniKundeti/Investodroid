package com.manu.investodroid.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manu.investodroid.R
import com.manu.investodroid.extensions.convertPriceToString
import com.manu.investodroid.model.Stock
import com.manu.investodroid.view.ui.main.MainActivity
import com.manu.investodroid.view.ui.stock_detail.StockDetailActivity
import kotlinx.android.synthetic.main.item_stock.view.*

class StocksListItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    fun bindView(stockModel : Stock){
        itemView.stock_name.text = stockModel.name
        itemView.stock_symbol.text = stockModel.symbol
        itemView.stock_price.text = stockModel.price.convertPriceToString()
    }
}

class StocksListAdapter(private val context: Context) : RecyclerView.Adapter<StocksListItemViewHolder>() {
    private var listOfStocks = listOf<Stock>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_stock,parent,false)
        return StocksListItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listOfStocks.size
    }

    override fun onBindViewHolder(holder: StocksListItemViewHolder, position: Int) {
       val stockViewHolder = holder
        stockViewHolder.bindView(listOfStocks[position])
        stockViewHolder.itemView.setOnClickListener {item ->
            var stockName = item.stock_name
//            Toast.makeText(mainActivity,"Stock Clicked is : $stockName",Toast.LENGTH_SHORT).show()
            val intent = Intent(context, StockDetailActivity::class.java)
            intent.putExtra("stock_symbol", listOfStocks[position].symbol)
            context.startActivity(intent)
        }
    }

    fun setStocksList(listOfStocks : List<Stock>){
        this.listOfStocks = listOfStocks
        notifyDataSetChanged()
    }
}