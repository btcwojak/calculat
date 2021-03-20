package com.spudg.kalk

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.spudg.kalk.databinding.CalculatorRowBinding
import java.util.*

class CalculatorAdapter(private val context: Context, private val items: ArrayList<CalculatorModel>) :
        RecyclerView.Adapter<CalculatorAdapter.CalculatorViewHolder>() {

    inner class CalculatorViewHolder(val binding: CalculatorRowBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorViewHolder {
        val binding = CalculatorRowBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return CalculatorViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CalculatorViewHolder, position: Int) {

        with(holder) {
            val calc = items[position]

            binding.calcName.text = calc.name
            binding.calcIcon.setImageDrawable(ContextCompat.getDrawable(context, calc.icon))

            binding.calculatorRowLayout.setOnClickListener {
                when (calc.name){
                    "mortgage" -> {
                        val intent = Intent(this, )
                    }
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


}