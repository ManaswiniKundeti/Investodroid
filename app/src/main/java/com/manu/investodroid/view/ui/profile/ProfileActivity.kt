package com.manu.investodroid.view.ui.profile

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.manu.investodroid.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val symbol = findViewById<TextView>(R.id.symbol)
        val companyName = findViewById<TextView>(R.id.company_name)
        val stockPrice = findViewById<TextView>(R.id.price)


    }
}