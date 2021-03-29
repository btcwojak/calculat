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
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.spudg.kalk.databinding.ActivityMortgageRepaymentCalculatorBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.pow

class MortgageRepaymentCalculator : AppCompatActivity() {

    private lateinit var bindingMortRepayCalc: ActivityMortgageRepaymentCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMortRepayCalc = ActivityMortgageRepaymentCalculatorBinding.inflate(layoutInflater)
        val view = bindingMortRepayCalc.root
        setContentView(view)

        val gbpFormatter: NumberFormat = DecimalFormat("£#,##0.00")
        val percentFormatter: NumberFormat = DecimalFormat("#,##0.00%")

        bindingMortRepayCalc.backToCalcListFromMortRepayCalc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindingMortRepayCalc.calcResultsLayout.visibility = View.GONE

        bindingMortRepayCalc.btnCalculate.setOnClickListener {

            if (bindingMortRepayCalc.etBorrowing.text.toString().isNotEmpty() && bindingMortRepayCalc.etInterest.text.toString().isNotEmpty() && bindingMortRepayCalc.etTerm.text.toString().isNotEmpty()) {
                this.currentFocus?.let { view ->
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }

                val borrowing = bindingMortRepayCalc.etBorrowing.text.toString().toFloat()
                val term = bindingMortRepayCalc.etTerm.text.toString().toFloat()
                val period = 12
                val interestRate = bindingMortRepayCalc.etInterest.text.toString().toFloat()/100

                bindingMortRepayCalc.totalBorrowing.text = gbpFormatter.format(borrowing)
                bindingMortRepayCalc.interestRate.text = percentFormatter.format(interestRate)
                bindingMortRepayCalc.term.text = "${term.toInt()} years"

                val monthlyPayment = (borrowing * (1 + interestRate / period).pow(term * period) * (interestRate / period) / ((1 + interestRate / period).pow(term * period) - 1))

                val monthlyLeftToPay: ArrayList<Float> = arrayListOf()
                monthlyLeftToPay.add(borrowing)
                var runningToRepay = borrowing
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

                bindingMortRepayCalc.totalInterest.text = gbpFormatter.format((monthlyPayment*term*period)-borrowing)
                bindingMortRepayCalc.monthlyRepayment.text = gbpFormatter.format(monthlyPayment)

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

        val chartLine: LineChart = bindingMortRepayCalc.leftToPayLineChart
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

        bindingMortRepayCalc.calcResultsLayout.visibility = View.VISIBLE

    }

}