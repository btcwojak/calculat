package com.spudg.kalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spudg.kalk.databinding.ActivityMortgageCalculatorBinding
import java.text.DecimalFormat
import java.text.NumberFormat

class MortgageCalculator : AppCompatActivity() {

    private lateinit var bindingMortCalc: ActivityMortgageCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMortCalc = ActivityMortgageCalculatorBinding.inflate(layoutInflater)
        val view = bindingMortCalc.root
        setContentView(view)

        val gbpFormatter: NumberFormat = DecimalFormat("£#,##0.00")
        val percentFormatter: NumberFormat = DecimalFormat("#,##0.00%")

        bindingMortCalc.btnCalculate.setOnClickListener {

            val housePrice = bindingMortCalc.etHousePrice.text.toString().toFloat()
            val deposit = bindingMortCalc.etDeposit.text.toString().toFloat()
            val term = bindingMortCalc.etTerm.text.toString().toFloat()
            val interestRate = bindingMortCalc.etInterest.text.toString().toFloat()/100

            val borrowing = housePrice - deposit

            bindingMortCalc.housePrice.text = gbpFormatter.format(housePrice)
            bindingMortCalc.deposit.text = gbpFormatter.format(deposit)
            bindingMortCalc.totalBorrowing.text = gbpFormatter.format(borrowing)
            bindingMortCalc.interestRate.text = percentFormatter.format(interestRate)
            bindingMortCalc.term.text = "${term.toInt()} years"

            var runningTotalInterest = 0F
            var osBalance = borrowing

            while(osBalance > 0) {
                runningTotalInterest += (osBalance * interestRate)
                osBalance -= (borrowing / term)
            }

            val totalInterest = runningTotalInterest

            val totalToRepay = borrowing + totalInterest
            val monthlyToRepay = totalToRepay / (term*12)

            bindingMortCalc.totalInterest.text = gbpFormatter.format(totalInterest)
            bindingMortCalc.monthlyRepayment.text = gbpFormatter.format(monthlyToRepay)


        }

    }

    fun currencyInputFilter(string: String, MAX_BEFORE_POINT: Int, MAX_DECIMAL: Int): String {
        var str = string
        if (str[0] == '.') str = "0$str"
        val max = str.length
        var rFinal = ""
        var after = false
        var i = 0
        var up = 0
        var decimal = 0
        var t: Char
        while (i < max) {
            t = str[i]
            if (t != '.' && !after) {
                up++
                if (up > MAX_BEFORE_POINT) return rFinal
            } else if (t == '.') {
                after = true
            } else {
                decimal++
                if (decimal > MAX_DECIMAL) return rFinal
            }
            rFinal += t
            i++
        }
        return rFinal
    }


}