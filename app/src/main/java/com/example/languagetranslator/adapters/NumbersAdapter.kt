package com.example.languagetranslator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.languagetranslator.data.Number
import com.example.languagetranslator.databinding.ItemNumberBinding
import com.example.languagetranslator.fragments.NumbersFragment

class NumbersAdapter(private val context: Context, private val items: ArrayList<Number>, private val listener: NumberAdapterListener): RecyclerView.Adapter<NumbersAdapter.NumbersViewHolder>() {
    inner class NumbersViewHolder(val binding: ItemNumberBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        return NumbersViewHolder(ItemNumberBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        holder.binding.engNumberTv.text = items[position].eng
        holder.binding.lanNumberTv.text = items[position].lan
        holder.binding.numberColor.setImageResource(items[position].id)
        holder.binding.numbersLayout.setOnClickListener {
            listener.onItemClicked(items[position].music)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
interface NumberAdapterListener{
    fun onItemClicked(id: Int)
}