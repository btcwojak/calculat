package com.spudg.kalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.spudg.kalk.databinding.ActivityMortgageCalculatorBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.pow

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
            val period = 12
            val interestRate = bindingMortCalc.etInterest.text.toString().toFloat()/100

            val borrowing = housePrice - deposit

            bindingMortCalc.housePrice.text = gbpFormatter.format(housePrice)
            bindingMortCalc.deposit.text = gbpFormatter.format(deposit)
            bindingMortCalc.totalBorrowing.text = gbpFormatter.format(borrowing)
            bindingMortCalc.interestRate.text = percentFormatter.format(interestRate)
            bindingMortCalc.term.text = "${term.toInt()} years"

            val monthlyPayment = (borrowing * (1 + interestRate / period).pow(term * period) * (interestRate / period) / ((1 + interestRate / period).pow(term * period) - 1))

            val monthlyLeftToPay: ArrayList<Float> = arrayListOf()
            monthlyLeftToPay.add(borrowing)
            var runningToRepay = borrowing
            repeat ((term*period).toInt()) {
                runningToRepay = runningToRepay + (runningToRepay*interestRate)/12 - monthlyPayment
                monthlyLeftToPay.add(runningToRepay)
            }

            val yearlyLeftToPay: ArrayList<Float> = arrayListOf()
            (0 until ((period*term)+1).toInt() step 12).asIterable()
                    .forEach { i ->
                        yearlyLeftToPay.add(monthlyLeftToPay[i])
                    }

            bindingMortCalc.totalInterest.text = gbpFormatter.format((monthlyPayment*term*period)-borrowing)
            bindingMortCalc.monthlyRepayment.text = gbpFormatter.format(monthlyPayment)

            setUpChart(yearlyLeftToPay)

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

        val chartLine: LineChart = bindingMortCalc.leftToPayLineChart
        if (entriesLine.size > 0) {
            chartLine.data = dataLine
        }


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



    }

}