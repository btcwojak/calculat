package com.spudg.kalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        calculators.add(CalculatorModel("Mortgage Repayment",R.drawable.mortgage))
        calculators.add(CalculatorModel("Mortgage Loan",R.drawable.loan))
        calculators.add(CalculatorModel("Compound Interest",R.drawable.compound_interest))
        calculators.add(CalculatorModel("APR %",R.drawable.investment_return))
        calculators.add(CalculatorModel("Percentages",R.drawable.percentage))
        val manager = LinearLayoutManager(this)
        bindingMain.rvCalculators.layoutManager = manager
        val calcAdapter = CalculatorAdapter(this, calculators)
        bindingMain.rvCalculators.adapter = calcAdapter
    }

    fun gotoMortgageCalc() {
        val intent = Intent(this, MortgageRepaymentCalculator::class.java)
        startActivity(intent)
    }

    fun gotoLoanCalc() {
        val intent = Intent(this, MortgageLoanCalculator::class.java)
        startActivity(intent)
    }

    fun gotoCompoundInterestCalc() {
        val intent = Intent(this, CompoundInterestCalculator::class.java)
        startActivity(intent)
    }

    fun gotoInvestmentReturnCalc() {
        val intent = Intent(this, InvestmentReturnCalculator::class.java)
        startActivity(intent)
    }

    fun gotoPercentageCalc() {
        val intent = Intent(this, PercentageCalculator::class.java)
        startActivity(intent)
    }

}
