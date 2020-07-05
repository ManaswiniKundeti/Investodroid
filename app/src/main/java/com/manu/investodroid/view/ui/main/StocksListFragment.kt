package com.manu.investodroid.view.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manu.investodroid.R
import com.manu.investodroid.extensions.hide
import com.manu.investodroid.extensions.show
import com.manu.investodroid.model.Stock
import com.manu.investodroid.view.adapter.StocksListAdapter
import com.manu.investodroid.viewmodel.MainActivityViewModel
import com.manu.investodroid.viewmodel.MainActivityViewModelFactory
import com.manu.investodroid.viewstate.Error
import com.manu.investodroid.viewstate.Loading
import com.manu.investodroid.viewstate.Success
import com.manu.investodroid.viewstate.ViewState
import kotlinx.android.synthetic.main.fragment_stocks_list.*

class StocksListFragment : Fragment() {

    private val viewmodelFactory by lazy { MainActivityViewModelFactory(requireContext()) }
    private val viewModel: MainActivityViewModel by viewModels {
        viewmodelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //inflate layour for the fragment
        val view = inflater.inflate(R.layout.fragment_stocks_list, container, false)

        //display & update recycler view
        val stocksList: RecyclerView = view.findViewById(R.id.stocksList)
        stocksList.layoutManager = LinearLayoutManager(requireContext())

        val stocksListAdapter = StocksListAdapter(requireContext() as MainActivity)
        stocksList.adapter = stocksListAdapter

        //create observer to update Ui after network calls
        val stocksListObserver = Observer<ViewState<List<Stock>>>{viewState ->
            when(viewState) {
                is Success -> {
                    stocks_list_progress_bar.hide()
                    stocksListAdapter.setStocksList(viewState.data)
                }
                is Error -> {
                    stocks_list_progress_bar.hide()
                    Toast.makeText(requireContext(), viewState.errMsg, Toast.LENGTH_SHORT).show()
                }
                is Loading -> {
                    stocks_list_progress_bar.show()
                }
            }
        }

        viewModel.stockLiveData.observe(viewLifecycleOwner, stocksListObserver)
        viewModel.getStocks()

        val searchEditText = view.findViewById<EditText>(R.id.search_edit_text)
        searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(editableText: Editable?) {
                if (editableText == null) {
                    return
                }

                val filterText = editableText.toString().trim()
                viewModel.filterStockList(filterText)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /* no op */
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /* no op */
            }

        })

        return view
    }

}