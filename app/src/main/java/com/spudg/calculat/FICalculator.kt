package com.spudg.calculat

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.spudg.calculat.databinding.ActivityFICalculatorBinding
import java.text.DecimalFormat
import java.text.NumberFormat

class FICalculator : AppCompatActivity() {

    private lateinit var bindingFICalc: ActivityFICalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingFICalc = ActivityFICalculatorBinding.inflate(layoutInflater)
        val view = bindingFICalc.root
        setContentView(view)

        val gbpFormatter: NumberFormat = DecimalFormat("£#,##0.00")
        val percentFormatter: NumberFormat = DecimalFormat("#,##0.00%")
        val figureFormatter: NumberFormat = DecimalFormat("#,##0.00")

        bindingFICalc.backToCalcListFromFICalc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindingFICalc.calcResultsLayout.visibility = View.GONE

        bindingFICalc.btnCalculate.setOnClickListener {

            val currentInvestments: Float = if (bindingFICalc.etCurrentInvestments.text.toString().isEmpty()) {
                0F
            } else {
                bindingFICalc.etCurrentInvestments.text.toString().toFloat()
            }

            val monthlyDeposit: Float = if (bindingFICalc.etMonthlyDeposit.text.toString().isEmpty()) {
                0F
            } else {
                bindingFICalc.etMonthlyDeposit.text.toString().toFloat()
            }

            val rateOfReturn: Float = if (bindingFICalc.etInterest.text.toString().isEmpty()) {
                0F
            } else {
                bindingFICalc.etInterest.text.toString().toFloat() / 100
            }

            val targetMonthlyInc: Float = if (bindingFICalc.etTargetMonthlyIncome.text.toString().isEmpty()) {
                0F
            } else {
                bindingFICalc.etTargetMonthlyIncome.text.toString().toFloat()
            }


            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }

            val fiNumber = (targetMonthlyInc*12)/.04
            var currentNumber = currentInvestments
            val endingMonthlyInvestments = arrayListOf<Float>()
            endingMonthlyInvestments.add(currentNumber)

            if (currentNumber > 0) {
                while (currentNumber < fiNumber) {
                    currentNumber += monthlyDeposit + ((currentNumber + monthlyDeposit) * (rateOfReturn/12))
                    endingMonthlyInvestments.add(currentNumber)
                }
            }

            val timeToFIMonths = (endingMonthlyInvestments.size-1)
            val timeToFIYears = figureFormatter.format(timeToFIMonths/12.00)

            bindingFICalc.currentInvestments.text = gbpFormatter.format(currentInvestments)
            bindingFICalc.monthlyContribution.text = gbpFormatter.format(monthlyDeposit)
            bindingFICalc.annualReturn.text = percentFormatter.format(rateOfReturn)
            bindingFICalc.targetMonthlyInc.text = gbpFormatter.format(targetMonthlyInc)
            bindingFICalc.fiNumber.text = gbpFormatter.format(fiNumber)
            bindingFICalc.timeToFI.text = "$timeToFIMonths months ($timeToFIYears years)"

            setUpChart(endingMonthlyInvestments)

            bindingFICalc.calcResultsLayout.visibility = View.VISIBLE


        }

    }


    private fun setUpChart(endingMonthlyInvestments: ArrayList<Float>) {

        val yearsInTerm: ArrayList<Int> = arrayListOf()
        repeat(endingMonthlyInvestments.size) {
            yearsInTerm.add(it)
        }

        val entriesLine: ArrayList<BarEntry> = arrayListOf()

        for (i in 0 until yearsInTerm.size) {
            entriesLine.add(BarEntry(yearsInTerm[i].toFloat(), endingMonthlyInvestments[i]))
        }

        val dataSetLine = LineDataSet(entriesLine as List<Entry>?, "")
        val dataLine = LineData(dataSetLine)
        dataSetLine.color = R.color.colorAccent

        val chartLine: LineChart = bindingFICalc.currentInvestmentsChart
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

        bindingFICalc.calcResultsLayout.visibility = View.VISIBLE

    }



}