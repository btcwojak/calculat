package com.spudg.kalk

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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
            binding.calcIcon.background = R.drawable.house.toDrawable()

            //binding.notesBtn.setOnClickListener {
            //    if (context is MainActivity) {
            //        context.viewNoteForRecordId(record.id)
            //    }
            //}

        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


}