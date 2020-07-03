package com.manu.investodroid.view.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.manu.investodroid.R
import com.manu.investodroid.extensions.convertPriceToString
import com.manu.investodroid.extensions.hide
import com.manu.investodroid.extensions.show
import com.manu.investodroid.model.FavouriteStock
import com.manu.investodroid.model.StockDetail
import com.manu.investodroid.viewmodel.ProfileActivityViewModel
import com.manu.investodroid.viewmodel.ProfileActivityViewModelFactory
import com.manu.investodroid.viewstate.Error
import com.manu.investodroid.viewstate.Loading
import com.manu.investodroid.viewstate.Success
import com.manu.investodroid.viewstate.ViewState
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val viewmodelFactory by lazy { ProfileActivityViewModelFactory(this) }
    private val viewModel: ProfileActivityViewModel by viewModels {
        viewmodelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val intent : Intent = intent
        val clickedStockSymbol = intent.getStringExtra("stock_symbol")
        if (clickedStockSymbol == null) {
            finish()
            return
        }

        val observer = Observer<ViewState<StockDetail>> { viewState ->
            when (viewState) {
                is Success -> {
                    profile_progress_bar.hide()
                    val stockDetail = viewState.data
                    company_name.text = stockDetail.profile.companyName
                    price.text = stockDetail.profile.price.convertPriceToString()
                    ceo_name.text = stockDetail.profile.ceo
                    headquarters_name.text =
                        getString(R.string.headquarters_text, stockDetail.profile.city, stockDetail.profile.state)
                    employees.text = stockDetail.profile.fullTimeEmployees
                    description_text_view.text = stockDetail.profile.description
//                    favouritesButton.setOnClickListener { item ->
//                        //update stock DB; set isFavourite = true
//                        val symbol = clickedStockSymbol
//                        viewModel.updateStock(symbol)
//                    }
                }
                is Error -> {
                    profile_progress_bar.hide()
                    Toast.makeText(this, viewState.errMsg, Toast.LENGTH_SHORT).show()

                }
                is Loading -> {
                    profile_progress_bar.show()
                }
            }
        }

        viewModel.detailLiveData.observe(this, observer)
        viewModel.fetchStockProfile(clickedStockSymbol)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favourites_menu,menu)
        return true
    }

    override fun onOptionsItemSelected( item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favouriteMenuItem ->{
                val intent : Intent = intent
                val clickedStockSymbol = intent.getStringExtra("stock_symbol")
                return if (clickedStockSymbol == null) {
                    finish()
                    false
                }else{
                    viewModel.insertStock(FavouriteStock(clickedStockSymbol))
                    true
                }
            }
            R.id.unFavouriteMenuItem ->{
                val intent : Intent = intent
                val clickedStockSymbol = intent.getStringExtra("stock_symbol")
                return if (clickedStockSymbol == null) {
                    finish()
                    false
                }else{
                    viewModel.deleteStock(FavouriteStock(clickedStockSymbol))
                    true
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}