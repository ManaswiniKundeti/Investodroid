package com.manu.investodroid.view.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.manu.investodroid.R
import com.manu.investodroid.extensions.convertPriceToString
import com.manu.investodroid.extensions.hide
import com.manu.investodroid.extensions.show
import com.manu.investodroid.model.Stock
import com.manu.investodroid.model.StockProfile
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

        viewModel.profileLiveData.observe(this, Observer<ViewState<StockProfile>>{ viewState ->
            when(viewState) {
                is Success -> {
                    profile_progress_bar.hide()
                    val stockProfile = viewState.data
                    company_name.text = stockProfile.companyName
                    price.text = stockProfile.price.convertPriceToString()
                    ceo_name.text = stockProfile.ceo
                    headquarters_name.text = getString(R.string.headquarters_text, stockProfile.city, stockProfile.state)
                    employees.text = stockProfile.fullTimeEmployees
                    description_text_view.text = stockProfile.description
                    favouritesButton.setOnClickListener { item ->
                        //update stock DB; set isFavourite = true
                        val symbol = clickedStockSymbol
                        viewModel.updateStock(symbol)
                    }
                }
                is Error -> {
                    profile_progress_bar.hide()
                    Toast.makeText(this, viewState.errMsg, Toast.LENGTH_SHORT).show()

                }
                is Loading -> {
                    profile_progress_bar.show()
                }
            }
        })
        viewModel.fetchStockProfile(clickedStockSymbol)
    }
}