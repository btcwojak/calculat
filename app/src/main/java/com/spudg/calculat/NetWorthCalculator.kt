package com.spudg.calculat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.spudg.calculat.databinding.ActivityNetWorthCalculatorBinding
import java.text.DecimalFormat
import java.text.NumberFormat

class NetWorthCalculator : AppCompatActivity() {

    private lateinit var bindingNetWorthCalc: ActivityNetWorthCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingNetWorthCalc = ActivityNetWorthCalculatorBinding.inflate(layoutInflater)
        val view = bindingNetWorthCalc.root
        setContentView(view)

        val gbpFormatter: NumberFormat = DecimalFormat("£#,##0.00")

        bindingNetWorthCalc.backToCalcListFromNetWorthCalc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindingNetWorthCalc.calcResultsLayout.visibility = View.GONE

        bindingNetWorthCalc.btnCalculate.setOnClickListener {

            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }

            // ASSETS

            val homeEquity: Float = if (bindingNetWorthCalc.etHomeEquity.text.toString().isEmpty()) {
                0F
            } else {
                bindingNetWorthCalc.etHomeEquity.text.toString().toFloat()
            }

            val cashSavings: Float = if (bindingNetWorthCalc.etCashSavings.text.toString().isEmpty()) {
                0F
            } else {
                bindingNetWorthCalc.etCashSavings.text.toString().toFloat()
            }

            val investments: Float = if (bindingNetWorthCalc.etInvestments.text.toString().isEmpty()) {
                0F
            } else {
                bindingNetWorthCalc.etInvestments.text.toString().toFloat()
            }

            val vehicles: Float = if (bindingNetWorthCalc.etVehicles.text.toString().isEmpty()) {
                0F
            } else {
                bindingNetWorthCalc.etVehicles.text.toString().toFloat()
            }

            val otherAssets: Float = if (bindingNetWorthCalc.etOtherAssets.text.toString().isEmpty()) {
                0F
            } else {
                bindingNetWorthCalc.etOtherAssets.text.toString().toFloat()
            }

            //LIABILITIES

            val creditCardDebt: Float = if (bindingNetWorthCalc.etCreditCardDebt.text.toString().isEmpty()) {
                0F
            } else {
                bindingNetWorthCalc.etCreditCardDebt.text.toString().toFloat()
            }

            val overdrafts: Float = if (bindingNetWorthCalc.etOverdrafts.text.toString().isEmpty()) {
                0F
            } else {
                bindingNetWorthCalc.etOverdrafts.text.toString().toFloat()
            }

            val studentLoans: Float = if (bindingNetWorthCalc.etStudentLoans.text.toString().isEmpty()) {
                0F
            } else {
                bindingNetWorthCalc.etStudentLoans.text.toString().toFloat()
            }

            val vehicleLoans: Float = if (bindingNetWorthCalc.etVehicleLoans.text.toString().isEmpty()) {
                0F
            } else {
                bindingNetWorthCalc.etVehicleLoans.text.toString().toFloat()
            }

            val otherLiabilities: Float = if (bindingNetWorthCalc.etOtherLiabilities.text.toString().isEmpty()) {
                0F
            } else {
                bindingNetWorthCalc.etOtherLiabilities.text.toString().toFloat()
            }

            val assets = homeEquity + cashSavings + investments + vehicles + otherAssets
            val liabilities = creditCardDebt + overdrafts + studentLoans + vehicleLoans + otherLiabilities
            val netWorth = assets - liabilities

            bindingNetWorthCalc.assets.text = gbpFormatter.format(assets)
            bindingNetWorthCalc.liabilities.text = gbpFormatter.format(liabilities)
            bindingNetWorthCalc.netWorth.text = gbpFormatter.format(netWorth)

            bindingNetWorthCalc.calcResultsLayout.visibility = View.VISIBLE

        }

    }


}