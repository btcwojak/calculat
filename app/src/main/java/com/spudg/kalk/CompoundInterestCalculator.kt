package com.spudg.kalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.spudg.kalk.databinding.ActivityCompoundInterestCalculatorBinding
import kotlin.math.pow

class CompoundInterestCalculator : AppCompatActivity() {


    private lateinit var bindingCICalc: ActivityCompoundInterestCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingCICalc = ActivityCompoundInterestCalculatorBinding.inflate(layoutInflater)
        val view = bindingCICalc.root
        setContentView(view)

        bindingCICalc.backToCalcListFromCICalc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindingCICalc.btnCalculate.setOnClickListener {

            val initialDeposit = bindingCICalc.etInitialLumpSum.text.toString().toFloat()
            val monthlyDeposit = bindingCICalc.etMonthlyDeposit.text.toString().toFloat()
            val termYears = bindingCICalc.etTerm.text.toString().toFloat()
            val rateOfReturn = bindingCICalc.etInterest.text.toString().toFloat()/100

            val compoundedInitialDeposit = initialDeposit*((1+(rateOfReturn/12)).pow(termYears*12))
            val compoundedMonthlyDeposits = monthlyDeposit*(((1+(rateOfReturn/12)).pow(12*termYears)-1)/(rateOfReturn/12))
            val totalEndingBalance = compoundedInitialDeposit + compoundedMonthlyDeposits
            val interestEarned = totalEndingBalance - initialDeposit - (monthlyDeposit*termYears*12)

            bindingCICalc.initialDeposit.text = initialDeposit.toString()
            bindingCICalc.monthlyContribution.text = monthlyDeposit.toString()
            bindingCICalc.annualInterest.text = (rateOfReturn*100).toString() + "%"
            bindingCICalc.termYears.text = termYears.toString()
            bindingCICalc.interestEarned.text = interestEarned.toString()
            bindingCICalc.endingBalance.text = totalEndingBalance.toString()


        }







    }
}