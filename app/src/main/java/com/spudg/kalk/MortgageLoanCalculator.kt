package com.spudg.kalk

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.spudg.kalk.databinding.ActivityMortgageLoanCalculatorBinding
import com.spudg.kalk.databinding.ActivityMortgageRepaymentCalculatorBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.pow

class MortgageLoanCalculator : AppCompatActivity() {

    private lateinit var bindingMortLoanCalc: ActivityMortgageLoanCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMortLoanCalc = ActivityMortgageLoanCalculatorBinding.inflate(layoutInflater)
        val view = bindingMortLoanCalc.root
        setContentView(view)

        val gbpFormatter: NumberFormat = DecimalFormat("£#,##0.00")
        val percentFormatter: NumberFormat = DecimalFormat("#,##0.00%")


        bindingMortLoanCalc.backToCalcListFromMortLoanCalc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindingMortLoanCalc.calcResultsLayout.visibility = View.GONE

        bindingMortLoanCalc.btnCalculate.setOnClickListener {

            if (bindingMortLoanCalc.etRepayment.text.toString().isNotEmpty() && bindingMortLoanCalc.etInterest.text.toString().isNotEmpty() && bindingMortLoanCalc.etTerm.text.toString().isNotEmpty()) {
                this.currentFocus?.let { view ->
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }

                val monthlyPayment = bindingMortLoanCalc.etRepayment.text.toString().toFloat()
                val term = bindingMortLoanCalc.etTerm.text.toString().toFloat()
                val period = 12
                val interestRate = bindingMortLoanCalc.etInterest.text.toString().toFloat()/100

                bindingMortLoanCalc.maxRepayment.text = gbpFormatter.format(monthlyPayment)
                bindingMortLoanCalc.interestRate.text = percentFormatter.format(interestRate)
                bindingMortLoanCalc.term.text = "${term.toInt()} years"

                val maxToBorrow = 1/(((1 + interestRate / period).pow(term * period) * (interestRate / period) / ((1 + interestRate / period).pow(term * period) - 1))*(1/monthlyPayment))

                val monthlyLeftToPay: ArrayList<Float> = arrayListOf()
                monthlyLeftToPay.add(maxToBorrow)
                var runningToRepay = maxToBorrow
                repeat ((term*period).toInt()) {
                    runningToRepay = runningToRepay + (runningToRepay*interestRate)/12 - monthlyPayment
                    monthlyLeftToPay.add(runningToRepay)
                }

                val length = monthlyLeftToPay.size
                if (monthlyLeftToPay[length-1] != 0F) {
                    monthlyLeftToPay[length-1] = 0F
                }

                val yearlyLeftToPay: ArrayList<Float> = arrayListOf()
                (0 until ((period*term)+1).toInt() step 12).asIterable()
                        .forEach { i ->
                            yearlyLeftToPay.add(monthlyLeftToPay[i])
                        }

                bindingMortLoanCalc.totalInterest.text = gbpFormatter.format((monthlyPayment*term*period)-maxToBorrow)
                bindingMortLoanCalc.maxToBorrow.text = gbpFormatter.format(maxToBorrow)

                setUpChart(yearlyLeftToPay)

            } else {
                Toast.makeText(this, "Make sure each field is filled in", Toast.LENGTH_SHORT).show()
            }



        }


    }

    private fun setUpChart(monthlyLeftToPay: ArrayList<Float>) {

        val yearsInTerm: ArrayList<Int> = arrayListOf()
        repeat (monthlyLeftToPay.size) {
            yearsInTerm.add(it)
        }

        val entriesLine: ArrayList<BarEntry> = arrayListOf()

        for (i in 0 until yearsInTerm.size) {
            entriesLine.add(BarEntry(yearsInTerm[i].toFloat(), monthlyLeftToPay[i]))
        }

        val dataSetLine = LineDataSet(entriesLine as List<Entry>?, "")
        val dataLine = LineData(dataSetLine)
        dataSetLine.color = R.color.colorAccent

        val chartLine: LineChart = bindingMortLoanCalc.leftToPayLineChart
        if (entriesLine.size > 0) {
            chartLine.data = dataLine
        }

        dataLine.setDrawValues(false)

        chartLine.animateY(800)
        chartLine.setNoDataText("No data yet.")
        chartLine.setNoDataTextColor(0xff000000.toInt())
        chartLine.setNoDataTextTypeface(ResourcesCompat.getFont(this, R.font.open_sans_light))
        chartLine.xAxis.setDrawGridLines(false)
        chartLine.axisRight.isEnabled = false
        chartLine.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chartLine.legend.isEnabled = false

        dataLine.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return if (value > 0) {
                    val mFormat = DecimalFormat("###,###,##0.00")
                    mFormat.format(super.getFormattedValue(value).toFloat())
                } else {
                    ""
                }
            }
        })

        chartLine.description.isEnabled = false

        val l: Legend = chartLine.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)

        chartLine.invalidate()

        bindingMortLoanCalc.calcResultsLayout.visibility = View.VISIBLE

    }

}