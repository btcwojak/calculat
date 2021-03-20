package com.spudg.kalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.spudg.kalk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingMain.root
        setContentView(view)

        setUpCalculatorList()

    }

    private fun setUpCalculatorList() {
        val calculators: ArrayList<CalculatorModel> = ArrayList()
        calculators.add(CalculatorModel("Mortgage",R.drawable.house))
        calculators.add(CalculatorModel("Loan",R.drawable.house))
        calculators.add(CalculatorModel("Compound Interest",R.drawable.money))
        calculators.add(CalculatorModel("Investment Return",R.drawable.house))
        calculators.add(CalculatorModel("Percentage",R.drawable.house))
        val manager = LinearLayoutManager(this)
        bindingMain.rvCalculators.layoutManager = manager
        val calcAdapter = CalculatorAdapter(this, calculators)
        bindingMain.rvCalculators.adapter = calcAdapter
    }

}
