package com.spudg.calculat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.spudg.calculat.databinding.ActivityCompoundInterestCalculatorBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.pow

class CompoundInterestCalculator : AppCompatActivity() {

    private lateinit var bindingCICalc: ActivityCompoundInterestCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingCICalc = ActivityCompoundInterestCalculatorBinding.inflate(layoutInflater)
        val view = bindingCICalc.root
        setContentView(view)

        val gbpFormatter: NumberFormat = DecimalFormat("£#,##0.00")
        val percentFormatter: NumberFormat = DecimalFormat("#,##0.00%")

        bindingCICalc.backToCalcListFromCICalc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindingCICalc.calcResultsLayout.visibility = View.GONE

        bindingCICalc.btnCalculate.setOnClickListener {

            val initialDeposit: Float = if (bindingCICalc.etInitialLumpSum.text.toString().isEmpty()) {
                0F
            } else {
                bindingCICalc.etInitialLumpSum.text.toString().toFloat()
            }

            val monthlyDeposit: Float = if (bindingCICalc.etMonthlyDeposit.text.toString().isEmpty()) {
                0F
            } else {
                bindingCICalc.etMonthlyDeposit.text.toString().toFloat()
            }

            val termYears: Float = if (bindingCICalc.etTerm.text.toString().isEmpty()) {
                0F
            } else {
                bindingCICalc.etTerm.text.toString().toFloat()
            }

            val rateOfReturn: Float = if (bindingCICalc.etInterest.text.toString().isEmpty()) {
                0F
            } else {
                bindingCICalc.etInterest.text.toString().toFloat() / 100
            }

            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }

            val compoundedInitialDeposit = initialDeposit * ((1 + (rateOfReturn / 12)).pow(termYears * 12))
            val compoundedMonthlyDeposits = monthlyDeposit * (((1 + (rateOfReturn / 12)).pow(12 * termYears) - 1) / (rateOfReturn / 12))
            val totalEndingBalance = compoundedInitialDeposit + compoundedMonthlyDeposits
            val interestEarned = totalEndingBalance - initialDeposit - (monthlyDeposit * termYears * 12)

            bindingCICalc.initialDeposit.text = gbpFormatter.format(initialDeposit)
            bindingCICalc.monthlyContribution.text = gbpFormatter.format(monthlyDeposit)
            bindingCICalc.annualInterest.text = percentFormatter.format(rateOfReturn)
            bindingCICalc.termYears.text = "${termYears.toInt()} years"
            bindingCICalc.interestEarned.text = gbpFormatter.format(interestEarned)
            bindingCICalc.endingBalance.text = gbpFormatter.format(totalEndingBalance)

            val monthlyRunningBalance: ArrayList<Float> = arrayListOf()
            var runningBalance = initialDeposit
            monthlyRunningBalance.add(runningBalance)
            repeat((termYears * 12).toInt()) {
                runningBalance += ((runningBalance * rateOfReturn) / 12) + monthlyDeposit
                monthlyRunningBalance.add(runningBalance)
            }

            val yearlyRunningBalance: ArrayList<Float> = arrayListOf()
            (0 until monthlyRunningBalance.size + 1 step 12).asIterable()
                    .forEach { i ->
                        Log.e("test", monthlyRunningBalance[i].toString())
                        Log.e("test", i.toString())
                        yearlyRunningBalance.add(monthlyRunningBalance[i])
                    }

            setUpChart(yearlyRunningBalance)


        }

    }

    private fun setUpChart(yearlyBalance: ArrayList<Float>) {

        val yearsInTerm: ArrayList<Int> = arrayListOf()
        repeat(yearlyBalance.size) {
            yearsInTerm.add(it)
        }

        val entriesLine: ArrayList<BarEntry> = arrayListOf()

        for (i in 0 until yearsInTerm.size) {
            entriesLine.add(BarEntry(yearsInTerm[i].toFloat(), yearlyBalance[i]))
        }

        val dataSetLine = LineDataSet(entriesLine as List<Entry>?, "")
        val dataLine = LineData(dataSetLine)
        dataSetLine.color = R.color.colorAccent

        val chartLine: LineChart = bindingCICalc.yearlyBalanceLineChart
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

        bindingCICalc.calcResultsLayout.visibility = View.VISIBLE

    }


}