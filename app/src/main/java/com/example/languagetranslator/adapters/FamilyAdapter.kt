package com.example.languagetranslator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.languagetranslator.data.Family
import com.example.languagetranslator.databinding.ItemFamilyBinding
import com.example.languagetranslator.fragments.FamilyFragment

class FamilyAdapter(private val context: Context, private val items: ArrayList<Family>, private val listener: FamilyAdapterListener) : RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder>() {
    inner class FamilyViewHolder(val binding: ItemFamilyBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        return FamilyViewHolder(ItemFamilyBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        holder.binding.engFamilyTv.text = items[position].eng
        holder.binding.lanFamilyTv.text = items[position].lan
        holder.binding.imageFamily.setImageResource(items[position].id)
        holder.binding.familyLayout.setOnClickListener {
            listener.onItemClicked(items[position].music)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
interface FamilyAdapterListener{
    fun onItemClicked(id: Int)
}