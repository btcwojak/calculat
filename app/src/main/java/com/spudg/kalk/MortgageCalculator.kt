package com.spudg.kalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.spudg.kalk.databinding.ActivityMainBinding
import com.spudg.kalk.databinding.ActivityMortgageCalculatorBinding

class MortgageCalculator : AppCompatActivity() {

    private lateinit var bindingMortCalc: ActivityMortgageCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMortCalc = ActivityMortgageCalculatorBinding.inflate(layoutInflater)
        val view = bindingMortCalc.root
        setContentView(view)

        bindingMortCalc.btnCalculate.setOnClickListener {

            var housePrice = bindingMortCalc.etHousePrice.text.toString().toLong()
            var deposit = bindingMortCalc.etDeposit.text.toString().toLong()
            var term = bindingMortCalc.etTerm.text.toString().toLong()
            var interestRate = bindingMortCalc.etInterest.text.toString().toLong()

            var borrowing = housePrice - deposit

            bindingMortCalc.housePrice.text = "House price - £$housePrice"
            bindingMortCalc.deposit.text = "Deposit - £$deposit"
            bindingMortCalc.totalBorrowing.text = "Borrowing - £$borrowing"
            bindingMortCalc.interestRate.text = "Interest rate - $interestRate%"
            bindingMortCalc.term.text = "Length - $term years"

            val annualInterest = borrowing * (interestRate/100F)
            Log.e("testAnnualInterest",annualInterest.toString())
            Log.e("testBorrowing",borrowing.toString())
            Log.e("testInterestRate",(interestRate/100F).toString())

            val totalInterest = annualInterest * term

            val totalToRepay = borrowing + totalInterest
            val monthlyToRepay = totalToRepay / (term*12)

            bindingMortCalc.totalInterest.text = "Total interest - £$totalInterest"
            bindingMortCalc.monthlyRepayment.text = "Monthly repayment - £$monthlyToRepay"


        }

    }
}