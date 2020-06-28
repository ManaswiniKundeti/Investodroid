package com.manu.investodroid.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.manu.investodroid.R
import com.manu.investodroid.model.Stock
import com.manu.investodroid.view.ui.main.MainActivity
import kotlinx.android.synthetic.main.item_stock.view.*

class StocksListItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    fun bindView(stockModel : Stock){
        itemView.stock_name.text = stockModel.name
        itemView.stock_symbol.text = stockModel.symbol
        itemView.stock_price.text = stockModel.price.toString()
    }
}

class StocksListAdapter(private val mainActivity: MainActivity) : RecyclerView.Adapter<StocksListItemViewHolder>() {
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
            Toast.makeText(mainActivity,"Stock Clicked is : $stockName",Toast.LENGTH_SHORT).show()
        }
    }

    fun setStocksList(listOfStocks : List<Stock>){
        this.listOfStocks = listOfStocks
        notifyDataSetChanged()
    }
}