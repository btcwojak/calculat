package com.spudg.kalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.github.mikephil.charting.formatter.PercentFormatter
import com.spudg.kalk.databinding.ActivityMortgageLoanCalculatorBinding
import com.spudg.kalk.databinding.ActivityPercentageCalculatorBinding
import java.lang.String.format
import java.text.DecimalFormat
import java.text.NumberFormat

class PercentageCalculator : AppCompatActivity() {

    private lateinit var bindingPercentageCalc: ActivityPercentageCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingPercentageCalc = ActivityPercentageCalculatorBinding.inflate(layoutInflater)
        val view = bindingPercentageCalc.root
        setContentView(view)

        val percentFormatter: NumberFormat = DecimalFormat("#,##0.00%")
        val figureFormatter: NumberFormat = DecimalFormat("#,##0.00")

        bindingPercentageCalc.backToCalcListFromPercentageCalc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // _% of _ is _ (Q1)
        bindingPercentageCalc.etQ1F1.addTextChangedListener {
            if (bindingPercentageCalc.etQ1F1.text.toString().isNotEmpty() && bindingPercentageCalc.etQ1F2.text.toString().isNotEmpty() && bindingPercentageCalc.etQ1F1.text.toString() != "." && bindingPercentageCalc.etQ1F2.text.toString() != ".") {
                var Q1F1 = bindingPercentageCalc.etQ1F1.text.toString().toFloat()
                var Q1F2 = bindingPercentageCalc.etQ1F2.text.toString().toFloat()
                var answer = (Q1F1/100)*Q1F2
                bindingPercentageCalc.Q1A1.text = figureFormatter.format(answer)
            }
        }
        bindingPercentageCalc.etQ1F2.addTextChangedListener {
            if (bindingPercentageCalc.etQ1F1.text.toString().isNotEmpty() && bindingPercentageCalc.etQ1F2.text.toString().isNotEmpty() && bindingPercentageCalc.etQ1F1.text.toString() != "." && bindingPercentageCalc.etQ1F2.text.toString() != ".") {
                var Q1F1 = bindingPercentageCalc.etQ1F1.text.toString().toFloat()
                var Q1F2 = bindingPercentageCalc.etQ1F2.text.toString().toFloat()
                var answer = (Q1F1/100)*Q1F2
                bindingPercentageCalc.Q1A1.text = figureFormatter.format(answer)
            }
        }

        // _ as a % of _ is _% (Q2)
        bindingPercentageCalc.etQ2F1.addTextChangedListener {
            if (bindingPercentageCalc.etQ2F1.text.toString().isNotEmpty() && bindingPercentageCalc.etQ2F2.text.toString().isNotEmpty() && bindingPercentageCalc.etQ2F1.text.toString() != "." && bindingPercentageCalc.etQ2F2.text.toString() != ".") {
                var Q2F1 = bindingPercentageCalc.etQ2F1.text.toString().toFloat()
                var Q2F2 = bindingPercentageCalc.etQ2F2.text.toString().toFloat()
                var answer = Q2F1/Q2F2
                bindingPercentageCalc.Q2A1.text = percentFormatter.format(answer)
            }
        }
        bindingPercentageCalc.etQ2F2.addTextChangedListener {
            if (bindingPercentageCalc.etQ2F1.text.toString().isNotEmpty() && bindingPercentageCalc.etQ2F2.text.toString().isNotEmpty() && bindingPercentageCalc.etQ2F1.text.toString() != "." && bindingPercentageCalc.etQ2F2.text.toString() != ".") {
                var Q2F1 = bindingPercentageCalc.etQ2F1.text.toString().toFloat()
                var Q2F2 = bindingPercentageCalc.etQ2F2.text.toString().toFloat()
                var answer = Q2F1/Q2F2
                bindingPercentageCalc.Q2A1.text = percentFormatter.format(answer)
            }
        }

        // _ to _ is _% (Q3)
        bindingPercentageCalc.etQ3F1.addTextChangedListener {
            if (bindingPercentageCalc.etQ3F1.text.toString().isNotEmpty() && bindingPercentageCalc.etQ3F2.text.toString().isNotEmpty() && bindingPercentageCalc.etQ3F1.text.toString() != "." && bindingPercentageCalc.etQ3F2.text.toString() != ".") {
                var Q3F1 = bindingPercentageCalc.etQ3F1.text.toString().toFloat() //3
                var Q3F2 = bindingPercentageCalc.etQ3F2.text.toString().toFloat() //6
                var answer = (Q3F2-Q3F1)/Q3F1
                bindingPercentageCalc.Q3A1.text = percentFormatter.format(answer)
            }
        }
        bindingPercentageCalc.etQ3F2.addTextChangedListener {
            if (bindingPercentageCalc.etQ3F1.text.toString().isNotEmpty() && bindingPercentageCalc.etQ3F2.text.toString().isNotEmpty() && bindingPercentageCalc.etQ3F1.text.toString() != "." && bindingPercentageCalc.etQ3F2.text.toString() != ".") {
                var Q3F1 = bindingPercentageCalc.etQ3F1.text.toString().toFloat()
                var Q3F2 = bindingPercentageCalc.etQ3F2.text.toString().toFloat()
                var answer = (Q3F2-Q3F1)/Q3F1
                bindingPercentageCalc.Q3A1.text = percentFormatter.format(answer)
            }
        }

        // _ is _% of _ (Q4)
        bindingPercentageCalc.etQ4F1.addTextChangedListener {
            if (bindingPercentageCalc.etQ4F1.text.toString().isNotEmpty() && bindingPercentageCalc.etQ4F2.text.toString().isNotEmpty() && bindingPercentageCalc.etQ4F1.text.toString() != "." && bindingPercentageCalc.etQ4F2.text.toString() != ".") {
                var Q4F1 = bindingPercentageCalc.etQ4F1.text.toString().toFloat()
                var Q4F2 = bindingPercentageCalc.etQ4F2.text.toString().toFloat()
                var answer = Q4F1/(Q4F2/100)
                bindingPercentageCalc.Q4A1.text = figureFormatter.format(answer)
            }
        }
        bindingPercentageCalc.etQ4F2.addTextChangedListener {
            if (bindingPercentageCalc.etQ4F1.text.toString().isNotEmpty() && bindingPercentageCalc.etQ4F2.text.toString().isNotEmpty() && bindingPercentageCalc.etQ4F1.text.toString() != "." && bindingPercentageCalc.etQ4F2.text.toString() != ".") {
                var Q4F1 = bindingPercentageCalc.etQ4F1.text.toString().toFloat()
                var Q4F2 = bindingPercentageCalc.etQ4F2.text.toString().toFloat()
                var answer = Q4F1/(Q4F2/100)
                bindingPercentageCalc.Q4A1.text = figureFormatter.format(answer)
            }
        }





    }


}