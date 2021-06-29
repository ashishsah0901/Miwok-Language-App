package com.example.languagetranslator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.languagetranslator.data.Color
import com.example.languagetranslator.databinding.ItemColorBinding
import com.example.languagetranslator.fragments.ColorsFragment

class ColorsAdapter(private val context: Context, private val items: ArrayList<Color>, private val listener: ColorsAdapterListener): RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder>() {
    inner class ColorsViewHolder(val binding: ItemColorBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        return ColorsViewHolder(ItemColorBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        holder.binding.engColorTv.text = items[position].eng
        holder.binding.lanColorTv.text = items[position].lan
        holder.binding.imageColor.setImageResource(items[position].id)
        holder.binding.colorsLayout.setOnClickListener {
            listener.onItemClicked(items[position].music)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
interface ColorsAdapterListener{
    fun onItemClicked(id: Int)
}